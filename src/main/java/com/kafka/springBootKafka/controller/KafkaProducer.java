package com.kafka.springBootKafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.springBootKafka.model.Payload;

@Controller
@RestController
@RequestMapping("kafka")
public class KafkaProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaMsgTemplate;
	
	@Autowired
	private KafkaTemplate<String, Payload> kafkaPayloadTemplate;
	
	private static final String MSG_TOPIC = "kafkatopic";
	
	private static final String PAYLOAD_TOPIC = "kafkatopicjson";
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	@PostMapping("/messages/{message}")
	public String writeMessage(@PathVariable("message") final String message) {
		
		try {
			kafkaMsgTemplate.send(MSG_TOPIC, message);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return "Publishaed message successfully!!";
	}
	
	@PostMapping("/payloads")
	public String writePayload(@RequestBody final String message) {
		
		Payload payload = null;
		try {
			payload = mapper.readValue(message, Payload.class);
			kafkaPayloadTemplate.send(PAYLOAD_TOPIC, payload);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return "Publishaed payload successfully!!";
	}
}