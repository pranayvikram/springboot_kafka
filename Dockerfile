FROM alpine:3.12

EXPOSE 8080

ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en' LC_ALL='en_US.UTF-8'

RUN apk add --no-cache curl && addgroup -S pranayvikram \
    && adduser -S pranayvikram -G pranayvikram --uid 24242
   
ENV JAVA_VERSION jdk-11.0.9.1_1

COPY slim-java* /usr/local/bin/

ENV JAVA_HOME=/opt/java/openjdk \
    PATH="/opt/java/openjdk/bin:$PATH"
CMD ["jshell"]
