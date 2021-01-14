package com.kafka.springBootKafka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Payload {

	@JsonProperty("id")
	Integer id;
	
	@JsonProperty("type")
	String type;
	
	@JsonProperty("value")
	String value;
	
	@JsonProperty("message")
	String message;
	
	public Payload() {}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Payload [id=" + id + ", type=" + type + ", value=" + value + ", message=" + message + "]";
	}
}