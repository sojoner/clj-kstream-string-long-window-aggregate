# clj-kstream-string-long-window-aggregate

A Clojure application to window aggregate over a 
^String ^Long kafka topic 

## Docker Hub

* [sojoner/clj-kstream-string-long-window-aggregate](https://hub.docker.com/r/sojoner/clj-kstream-string-long-window-aggregate/) 

## Requirements

* [leiningen 2.7.1](https://leiningen.org/)
* [kafka 0.10.0.1](http://kafka.apache.org) 
* [docker 1.12.6](https://www.docker.com/)

### Kafka Topic

The topic you are consuming needs to have **^String** Keys and **^Long** .json values.  

## Build Clojure
    
    $lein check

## Build .jar

    $lein uberjar

## Build .container
    
    $cd deploy
    $./containerize.sh

## Usage Leiningen

    $lein run --broker kafka-broker:9092 --input-topic heavy-hitters --window-size 10 --output-topic agg-result --name stream-agg

## Usage java

    $java - jar clj-kstream-string-long-window-aggregate-0.1.0-SNAPSHOT-standalone.jar --broker kafka-broker:9092 --input-topic hh-sink  --window-size 10 --output-topic agg-result --name stream-agg

## Usage docker

    $docker run -t -i <BUILD-HASH> --network cljkstream_network  --broker kafka-broker:9092 --input-topic mapped-test-json --output-topic heavy-hitters  --window-size 10 --name stream-hh

## License

Copyright © 2017 Hagen Tönnies

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
