package com.example.week1.part3;

import java.util.Objects;

public class Message {
	final EventType eventType;

	public Message(EventType eventType) {
		this.eventType = eventType;
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
