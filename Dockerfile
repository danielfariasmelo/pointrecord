FROM openjdk:8-jre-alpine

ENV LANG C.UTF-8

WORKDIR /app
COPY build/libs/pointrecord-*.jar app.jar

ENTRYPOINT exec java \
-XX:+UnlockExperimentalVMOptions \
-XX:+UseCGroupMemoryLimitForHeap \
$JAVA_OPTS \
-Djava.security.egd=file:/dev/./urandom \
-jar app.jar \
$APP_ARGS