package com.example.week1.part4.done;

import java.util.Objects;

public class Message {
	final EventType eventType;

	final String applierName;

	public Message(EventType eventType) {
		this.eventType = eventType;
		this.applierName = null;
	}

	public Message(EventType eventType, String applierName) {
		this.eventType = eventType;
		this.applierName = applierName;
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
		return eventType == message1.eventType && Objects.equals(applierName, message1.applierName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(eventType, applierName);
	}

	@Override
	public String toString() {
		return "Message{" +
				"eventType=" + eventType +
				", applierName='" + applierName + '\'' +
				'}';
	}
}
