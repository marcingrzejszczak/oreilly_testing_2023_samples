package com.example.week2.part4.done;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
	private final EventType eventType;

	@JsonCreator
	public Message(@JsonProperty("eventType") EventType eventType) {
		this.eventType = eventType;
	}

	public EventType getEventType() {
		return eventType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Message message = (Message) o;
		return eventType == message.eventType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(eventType);
	}

	@Override
	public String toString() {
		return "Message{" +
				"eventType=" + eventType +
				'}';
	}
}
