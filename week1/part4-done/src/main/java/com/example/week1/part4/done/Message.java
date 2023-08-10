package com.example.week1.part4.done;

import java.util.Objects;

public class Message {
	final EventType eventType;

	final String message;

	public Message(EventType eventType) {
		this.eventType = eventType;
		this.message = null;
	}

	public Message(EventType eventType, String message) {
		this.eventType = eventType;
		this.message = message;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Message message1 = (Message) o;
		return eventType == message1.eventType && Objects.equals(message, message1.message);
	}

	@Override
	public int hashCode() {
		return Objects.hash(eventType, message);
	}

	@Override
	public String toString() {
		return "Message{" +
				"eventType=" + eventType +
				", message='" + message + '\'' +
				'}';
	}
}
