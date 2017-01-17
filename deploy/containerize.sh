#!/bin/bash
mv ../target/clj-kstream-string-long-window-aggregate.jar .
docker build --tag "sojoner/clj-kstream-string-long-window-aggregate:0.1.0" .
