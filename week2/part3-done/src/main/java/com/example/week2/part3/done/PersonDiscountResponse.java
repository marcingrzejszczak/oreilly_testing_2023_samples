package com.example.week2.part3.done;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

record PersonDiscountResponse(String name, double discountRate) {

	@JsonCreator
	PersonDiscountResponse(@JsonProperty("name") String name, @JsonProperty("discountRate") double discountRate) {
		this.name = name;
		this.discountRate = discountRate;
	}

}
