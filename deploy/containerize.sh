#!/bin/bash
mv ../target/clj-kstream-string-long-window-aggregate.jar .
docker build --tag "sojoner/clj-kstream-string-long-window-aggregate:0.3.0" .

#docker tag <HASH> sojoner/clj-kstream-string-long-window-aggregate:0.3.0
#docker login
#docker push sojoner/clj-kstream-string-long-window-aggregate
