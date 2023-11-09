package com.example.week3.part2.client.done;

import java.net.http.HttpClient;
import java.time.Duration;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import org.springframework.cloud.contract.stubrunner.junit.StubRunnerExtension;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;

class PersonClientTests {
	@RegisterExtension
	static StubRunnerExtension rule = new StubRunnerExtension()
			.downloadStub("com.example.testingworkshop","discount-calculator-done")
			.stubsMode(StubRunnerProperties.StubsMode.LOCAL);

	ObjectMapper objectMapper = new ObjectMapper();

	PersonClient personClient = new PersonClient(HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(1)).build(),
			rule.findStubUrl("discount-calculator-done").toString(),
			objectMapper);

	@Test
	void should_parse_the_response_for_bad_request_status() {
		Person person = new Person("mary",
				0, Occupation.EMPLOYED);

		DiscountResponse discountResponse = personClient.discount(person);

		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(discountResponse.personName()).isNull();
			softAssertions.assertThat(discountResponse.discountRate()).isZero();
			softAssertions.assertThat(discountResponse.person()).isEqualTo(person);
			softAssertions.assertThat(discountResponse.additionalMessage())
					.isEqualTo("We can't apply discounts to "
							+ "people who didn't buy any goods");
		});
	}

	@Test
	void should_parse_the_response_for_ok_status() {
		Person person = new Person("john", 5, Occupation.EMPLOYED);

		DiscountResponse discountResponse = personClient.discount(person);

		SoftAssertions.assertSoftly(softAssertions -> {
			softAssertions.assertThat(discountResponse.personName()).isEqualTo("john");
			softAssertions.assertThat(discountResponse.discountRate()).isEqualTo(10);
			softAssertions.assertThat(discountResponse.person()).isNull();
			softAssertions.assertThat(discountResponse.additionalMessage()).isNull();
		});
	}
}
