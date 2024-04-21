#FROM apache/flink:1.18.1-java17
FROM apache/flink:1.16.1-java11

ARG jarFileName

COPY --chown=flink:flink $jarFileName /opt/flink/lib/job.jar
COPY --chown=flink:flink $jarFileName /opt/flink/upload-jar/flink-web-upload/f00_my-app.jar

RUN chmod -R 777 /opt/flink

USER flink

ENV FLINK_CONF=/opt/flink/conf/flink-conf.yaml
