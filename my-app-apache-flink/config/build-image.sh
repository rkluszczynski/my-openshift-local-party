#!/usr/bin/env bash

FILENAME=my-app-apache-flink-0.1.0-SNAPSHOT-all.jar
NAMESPACE=my-app-test

cp ../build/libs/${FILENAME} . || exit 1
podman build \
  --build-arg jarFileName=${FILENAME} \
  -t io.github.rkluszczynski/my-app-af-process .

rm ${FILENAME}

podman tag io.github.rkluszczynski/my-app-af-process:latest \
  default-route-openshift-image-registry.apps-crc.testing/${NAMESPACE}/myappafp:latest

podman push default-route-openshift-image-registry.apps-crc.testing/${NAMESPACE}/myappafp:latest
