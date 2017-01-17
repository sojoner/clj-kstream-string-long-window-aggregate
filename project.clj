(defproject clj-kstream-string-long-window-aggregate "0.2.2"
  :description "A Clojure application to window aggregate over a \n^String ^Long kafka topic "
  :url "http://github.com/sojoner/clj-kstream-string-long-window-aggregate"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha14"]
                 [org.clojure/data.json "0.2.6"]
                 [org.clojure/tools.cli "0.3.5"]
                 [clj-time "0.13.0"]
                 ;; https://mvnrepository.com/artifact/org.apache.kafka/kafka_2.10
                 [org.apache.kafka/kafka_2.10 "0.10.0.1"]
                 ;; https://mvnrepository.com/artifact/org.apache.kafka/kafka-streams
                 [org.apache.kafka/kafka-streams "0.10.0.0"]
                 [org.clojure/tools.logging "0.2.6"]
                 [org.slf4j/slf4j-log4j12 "1.7.1"]
                 [log4j/log4j "1.2.17" :exclusions [javax.mail/mail
                                                    javax.jms/jms
                                                    com.sun.jmdk/jmxtools
                                                    com.sun.jmx/jmxri]]]
  :aot :all
  :main clj-kstream-string-long-window-aggregate.core
  :profiles {:uberjar {:aot :all}}
  ;; As above, but for uberjar.
  :uberjar-name "clj-kstream-string-long-window-aggregate.jar"
  :jvm-opts ["-Xmx2g" "-server"])
