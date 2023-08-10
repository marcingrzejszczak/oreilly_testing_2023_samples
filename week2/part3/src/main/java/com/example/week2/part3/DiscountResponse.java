package com.example.week2.part3;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

record DiscountResponse(String personName, double discountRate) {

	@JsonCreator
	DiscountResponse(@JsonProperty("personName") String personName, @JsonProperty("discountRate") double discountRate) {
		this.personName = personName;
		this.discountRate = discountRate;
	}

}
