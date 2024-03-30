#!/usr/bin/env bash

cp build/libs/my-app-spring-boot-0.0.1-SNAPSHOT.jar . || exit 1
podman build \
  --build-arg jarFileName=my-app-spring-boot-0.0.1-SNAPSHOT.jar \
  -t eu.europa.schengen.example/example-query-service .

rm my-app-spring-boot-0.0.1-SNAPSHOT.jar
