package com.kafka.springBootKafka.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kafka.springBootKafka.model.Payload;

@Service
public class KafkaConsumer {

	@KafkaListener(topics = "kafkatopic", groupId = "group_id")
	public void consume(String message) {
		System.out.println("New message consumed: " + message);
	}
	
	@KafkaListener(topics = "kafkatopicjson", groupId = "group_json", containerFactory = "payloadListenerContainerFactory")
	public void consume(Payload message) {
		System.out.println("New payload consumed: " + message);
	}
}