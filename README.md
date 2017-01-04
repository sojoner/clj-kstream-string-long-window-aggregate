# clj-kstream-string-long-window-aggregate

A Clojure application to window aggregate over a 
^String ^Long kafka topic 

## Usage

    $lein run --broker kafka-broker:9092 --zookeeper zookeeper:2181 --input-topic hh-sink --output-topic agg-result --name stream-cut-json-field

## License

Copyright © 2017 Hagen Tönnies

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
