#From java alpine base image
FROM alpine:3.12

MAINTAINER pranayvikram

EXPOSE 8080

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en' LC_ALL='en_US.UTF-8'

RUN apk add --no-cache curl \
    && addgroup -S pranayvikram \
    && adduser -S pranayvikram -G pranayvikram --uid 24242
   
ENV JAVA_VERSION jdk-11.0.9.1_1

COPY target/springBootKafka-0.0.1-SNAPSHOT.jar springBootKafka.jar

ENV JAVA_HOME=/opt/java/openjdk \
    PATH="/opt/java/openjdk/bin:$PATH"

CMD ["jshell"]
