package com.example.week3.part2.client.done;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

record DiscountResponse(String personName, double discountRate, Person person, String additionalMessage) {
	@JsonCreator
	DiscountResponse(@JsonProperty("personName") String personName, @JsonProperty("discountRate") double discountRate, @JsonProperty("person") Person person, @JsonProperty("additionalMessage") String additionalMessage) {
		this.personName = personName;
		this.discountRate = discountRate;
		this.person = person;
		this.additionalMessage = additionalMessage;
	}

}
