#!/bin/bash
mv ../target/clj-kstream-string-long-window-aggregate.jar .
docker build --tag "sojoner/clj-kstream-string-long-window-aggregate:0.2.2" .

#docker tag <HASH> sojoner/clj-kstream-string-long-window-aggregate:0.2.2
#docker login
#docker push sojoner/clj-kstream-string-long-window-aggregate
