#!/usr/bin/env bash

NAMESPACE=my-app-test

cp ../build/libs/my-app-spring-boot-0.0.1-SNAPSHOT.jar . || exit 1
podman build \
  --build-arg jarFileName=my-app-spring-boot-0.0.1-SNAPSHOT.jar \
  -t io.github.rkluszczynski/my-app-sb-service .

rm my-app-spring-boot-0.0.1-SNAPSHOT.jar

podman tag io.github.rkluszczynski/my-app-sb-service:latest \
  default-route-openshift-image-registry.apps-crc.testing/${NAMESPACE}/myappsbs:latest

podman push default-route-openshift-image-registry.apps-crc.testing/${NAMESPACE}/myappsbs:latest
