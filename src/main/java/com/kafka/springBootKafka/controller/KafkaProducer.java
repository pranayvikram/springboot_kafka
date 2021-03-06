package com.kafka.springBootKafka.controller;

import com.kafka.springBootKafka.service.KafkaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.springBootKafka.model.Payload;

@RestController
@RequestMapping("/")
public class KafkaProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaMsgTemplate;

	@Autowired
	private KafkaTemplate<String, Payload> kafkaPayloadTemplate;

	@Autowired
	private KafkaUserDetailsService userDetails;

	private static final String MSG_TOPIC = "kafkatopic";

	private static final String PAYLOAD_TOPIC = "kafkatopicjson";

	private static final ObjectMapper mapper = new ObjectMapper();

	@PostMapping("kafka/messages/{message}")
	public String writeMessage(@PathVariable("message") final String message) {

		try {
			kafkaMsgTemplate.send(MSG_TOPIC, message);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return "Published message successfully!!";
	}

	@PostMapping("kafka/payloads")
	public String writePayload(@RequestBody final String message) {

		Payload payload = null;
		try {
			payload = mapper.readValue(message, Payload.class);
			kafkaPayloadTemplate.send(PAYLOAD_TOPIC, payload);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return "Published payload successfully!!";
	}

	@GetMapping("kafka/{hello}")
	public String hello() {
		return "Hello You!!";
	}

	/*
	 * @Autowired RestTemplate restTemplate;
	 * 
	 * @RequestMapping(value = "/test/payload", method = RequestMethod.POST) public
	 * String createProducts(@RequestBody Payload payload) {
	 * 
	 * String url = "http://localhost:8080/products"; HttpHeaders headers = new
	 * HttpHeaders(); headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	 * HttpEntity<Payload> entity = new HttpEntity<Payload>(payload, headers);
	 * return restTemplate.exchange(url, HttpMethod.POST, entity,
	 * String.class).getBody(); }
	 */
}