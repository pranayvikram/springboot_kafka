package com.kafka.springBootKafka.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.errors.TimeoutException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.kafka.springBootKafka.model.Payload;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableKafka
@Configuration
public class KafkaConfiguration {

	@Bean
	ConsumerFactory<String, String> stringConsumerFactory() {
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return new DefaultKafkaConsumerFactory<String, String>(config);
	}
	
	@Bean
	ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(stringConsumerFactory());
		factory.setRetryTemplate(retryTemplate());
		factory.setRecoveryCallback((context -> {
			if (context.getLastThrowable().getCause() instanceof RecoverableDataAccessException) {
				// here we can implement recovery mechanism where 
				// we may put back on to the topic using a Kafka producer
			} else {
				// here we can log things and throw some custom exception that Error handler will take care
				throw new RuntimeException(context.getLastThrowable().getMessage());
			} 
			return null;
		}));
		factory.setErrorHandler(((exception, data) -> {
			// here we can do you custom handling, I am just logging it same as default
			// Error handler does. If only logging is required, we should not configure the error handler
			// The default handler does it for you. Generally, we only persist the failed records to DB for tracking.
			log.error("Error in process with Exception {} and the record is {}", exception, data);
		}));
		return factory;
	}
	
	@Bean
	ConsumerFactory<String, Payload> payloadConsumerFactory() {
		Map<String, Object> config = new HashMap<>();
		config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_json");
		config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(config,
				new StringDeserializer(), new JsonDeserializer<>(Payload.class));
	}
	
	@Bean
	ConcurrentKafkaListenerContainerFactory<String, Payload> payloadListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Payload> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(payloadConsumerFactory());
		factory.setRetryTemplate(retryTemplate());
		factory.setRecoveryCallback((context -> {
			if (context.getLastThrowable().getCause() instanceof RecoverableDataAccessException) {
				// here we can implement recovery mechanism where 
				// we may put back on to the topic using a Kafka producer
			} else {
				// here we can log things and throw some custom exception that Error handler will take care
				throw new RuntimeException(context.getLastThrowable().getMessage());
			}
			return null;
		}));
		factory.setErrorHandler(((exception, data) -> {
			// here we can do you custom handling, I am just logging it same as default
			// Error handler does. If only logging is required, we should not configure the error handler
			// The default handler does it for you. Generally, we only persist the failed records to DB for tracking.
			log.error("Error in process with Exception {} and the record is {}", exception, data);
		}));
		return factory;
	}

	@Bean
	ProducerFactory<String, String> messageProducerFactory() {
		Map<String, Object> config = new HashMap<>();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return new DefaultKafkaProducerFactory<>(config);
	}
	
	@Bean
	public KafkaTemplate<String, String> kafkaMessageTemplate() {
		return new KafkaTemplate<>(messageProducerFactory());
	}
	
	@Bean
	ProducerFactory<String, Payload> payloadProducerFactory() {
		Map<String, Object> config = new HashMap<>();
		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return new DefaultKafkaProducerFactory<>(config);
	}
	
	@Bean
	public KafkaTemplate<String, Payload> kafkaPayloadTemplate() {
		return new KafkaTemplate<>(payloadProducerFactory());
	}
	
	private RetryTemplate retryTemplate() {
		RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(getSimpleRetryPolicy());
        return retryTemplate;
    }
	
	private SimpleRetryPolicy getSimpleRetryPolicy() {
        Map<Class<? extends Throwable>, Boolean> exceptionMap = new HashMap<>();
        exceptionMap.put(IllegalArgumentException.class, false);
        exceptionMap.put(TimeoutException.class, true);
        return new SimpleRetryPolicy(3,exceptionMap,true);
    }
}