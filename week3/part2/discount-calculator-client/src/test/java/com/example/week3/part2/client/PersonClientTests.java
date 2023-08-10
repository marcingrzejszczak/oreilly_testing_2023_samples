package com.example.week3.part2.client;

import java.net.http.HttpClient;
import java.time.Duration;

import com.example.week3.part2.client.DiscountResponse;
import com.example.week3.part2.client.Occupation;
import com.example.week3.part2.client.Person;
import com.example.week3.part2.client.PersonClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class PersonClientTests {

	//TODO: Fix me - add StubRunner extension

	ObjectMapper objectMapper = new ObjectMapper();

	PersonClient personClient = new PersonClient(HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(1)).build(),
			null,  //TODO: Fix me - read URL from StubRunner extension
			objectMapper);

	@Test
	void should_parse_the_response_for_bad_request_status() {
		Person person = new Person("mary",
				0, Occupation.EMPLOYED);

		DiscountResponse discountResponse = personClient.discount(person);

		SoftAssertions.assertSoftly(softAssertions -> {
			throw new AssertionError("Fix me!"); //TODO: Fix me - assert that the response got properly parsed
		});
	}

	@Test
	void should_parse_the_response_for_ok_status() {
		Person person = new Person("john", 5, Occupation.EMPLOYED);

		DiscountResponse discountResponse = personClient.discount(person);

		SoftAssertions.assertSoftly(softAssertions -> {
			throw new AssertionError("Fix me!"); //TODO: Fix me - assert that the response got properly parsed
		});
	}
}
