(ns clj-kstream-string-long-window-aggregate.core
  (:use [clojure.tools.logging :only (debug info error warn)])
  (:require [clojure.data.json :as json]
            [clj-kstream-string-long-window-aggregate.cli :as cli-def]
            [clojure.tools.cli :as cli])
  (:import (org.apache.kafka.streams KafkaStreams
                                     StreamsConfig KeyValue)
           (org.apache.kafka.streams.kstream KStream
                                             KStreamBuilder
                                             KTable
                                             KeyValueMapper
                                             ForeachAction
                                             ValueMapper
                                             Aggregator
                                             TimeWindows
                                             Initializer)
           (org.apache.kafka.streams.processor AbstractProcessor)
           (org.apache.kafka.common.serialization Deserializer
                                                  Serde
                                                  Serdes
                                                  Serializer
                                                  LongSerializer
                                                  LongDeserializer
                                                  StringDeserializer
                                                  StringSerializer)
           (java.util Properties)
           (java.util.function Function))
  (:gen-class))

(def string_ser
  "The Serializer"
  (StringSerializer.))

(def string_dser
  "The de-serializer"
  (StringDeserializer.))

(def stringSerde
  "The serialization pair"
  (Serdes/serdeFrom string_ser string_dser))


(def longSerde
  (Serdes/serdeFrom (new LongSerializer), (new LongDeserializer)))

(defn- get-props [conf]
  "The kafka properties"
  (doto (new Properties)
    (.put StreamsConfig/APPLICATION_ID_CONFIG (:name conf))
    (.put StreamsConfig/BOOTSTRAP_SERVERS_CONFIG (:kafka-brokers conf))
    (.put StreamsConfig/ZOOKEEPER_CONNECT_CONFIG (:zookeeper-servers conf))))

(defn- stream-mapper
  "Main stream processor takes a configuration and a mapper function to apply."
  [conf ]
  (let [streamBuilder (KStreamBuilder.)
        ^KStream a-stream (.stream
                              streamBuilder
                              stringSerde
                              stringSerde
                              (into-array String [(:input-topic conf)]))]
    (-> a-stream
        (.aggregateByKey (reify Initializer
                           (apply [this] 0))

                         (reify Aggregator
                           (apply [this key value aggregate]
                             (+ aggregate (Long/parseLong value))))
                         (TimeWindows/of "counts" 5000)
                         stringSerde
                         longSerde)
        (.toStream)
        (.map (reify KeyValueMapper
                (apply [this key value]
                  (let[processing-time (quot (System/currentTimeMillis) 1000)
                       result {:token (.key key)
                               :count value
                               :processing-time processing-time}
                       new-key (str key "-" processing-time)
                       result_as_string (json/write-str result)]
                    (KeyValue. new-key result_as_string)))))
        (.to stringSerde stringSerde (:output-topic conf)))

    (.start (KafkaStreams. streamBuilder (get-props conf)))))

(defn -main [& args]
  (info "Start")
  (let [{:keys [options arguments errors summary]} (cli/parse-opts args cli-def/cli-options)
        conf {:kafka-brokers         (:broker options)
              :zookeeper-servers   (:zookeeper options)
              :input-topic (:input-topic options)
              :output-topic   (:output-topic options)
              :name (:name options)}]
    (cond
      (:help options) (cli-def/exit 0 (cli-def/usage summary))
      (not= (count (keys options)) 5) (cli-def/exit 1 (cli-def/usage summary))
      (not (nil? errors)) (cli-def/exit 1 (cli-def/error-msg errors)))
    (stream-mapper conf))
  (info "Done"))
