FROM ibm-semeru-runtimes:open-21-jre-centos7

ARG jarFileName

COPY ${jarFileName} /opt/

ENV APP_JAR_PATH="/opt/${jarFileName}"
ENV APP_ADDITIONAL_ARGS=""

ENTRYPOINT java -jar $APP_JAR_PATH $APP_ADDITIONAL_ARGS
