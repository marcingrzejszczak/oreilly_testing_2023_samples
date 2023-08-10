package com.example.week3.part2.calculator;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class CustomResponse {
	private final Person person;

	private final String additionalMessage;

	@JsonCreator
	public CustomResponse(@JsonProperty("person") Person person) {
		this.person = person;
		this.additionalMessage = "We can't apply discounts to people who didn't buy any goods";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CustomResponse that = (CustomResponse) o;
		return Objects.equals(person, that.person) && Objects.equals(additionalMessage, that.additionalMessage);
	}

	@Override
	public int hashCode() {
		return Objects.hash(person, additionalMessage);
	}

	@Override
	public String toString() {
		return "CustomResponse{" +
				"person=" + person +
				", additionalMessage='" + additionalMessage + '\'' +
				'}';
	}

	public Person getPerson() {
		return person;
	}

	public String getAdditionalMessage() {
		return additionalMessage;
	}
}
