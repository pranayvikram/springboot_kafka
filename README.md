# springboot_kafka

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.1/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.1/maven-plugin/reference/html/#build-image)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/docs/2.4.1/reference/htmlsingle/#boot-features-kafka)
* [Apache Kafka Streams Support](https://docs.spring.io/spring-kafka/docs/current/reference/html/_reference.html#kafka-streams)
* [Apache Kafka Streams Binding Capabilities of Spring Cloud Stream](https://docs.spring.io/spring-cloud-stream/docs/current/reference/htmlsingle/#_kafka_streams_binding_capabilities_of_spring_cloud_stream)

### Guides
The following guides illustrate how to use some features concretely:

* [Samples for using Apache Kafka Streams with Spring Cloud stream](https://github.com/spring-cloud/spring-cloud-stream-samples/tree/master/kafka-streams-samples)

### Launch separate power shell windows for all of these
# Star Zookeeper
bin\windows\zookeeper-server-start.bat config\zookeeper.properties

# Start Kafka Server
bin\windows\kafka-server-start.bat config\server.properties

# Create first topic
bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic kafkatopic

# Create second topic
bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic kafkatopicjson

# List topics
bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092

# Start Producer
bin\windows\kafka-console-producer.bat --bootstrap-server localhost:9092 --topic kafkatopic

# Start Consumer
bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic kafkatopic --from-beginning

## Request URL - 1 
http://localhost:8081/kafka/messages/test-message


## Request URL - 2
http://localhost:8081/kafka/payloads/
{
    "id": 1002,
    "type": "test",
    "value": "Hello message",
    "message": "This is testing"
}
