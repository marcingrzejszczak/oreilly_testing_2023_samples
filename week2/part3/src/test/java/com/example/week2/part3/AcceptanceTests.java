package com.example.week2.part3;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.BDDAssertions.then;

// TODO: Fix me - Start a WireMock instance on port 12345
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Week2Part3.class, properties = "person.url=http://localhost:12345")
class AcceptanceTests {

	@LocalServerPort
	int port;
	
	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	void setupWireMock() {
		WireMock.stubFor(WireMock.post("/person/foo")
				// TODO: Fix me - add application/json content-type matching
				// TODO: Fix me - add json body matching to person with name foo number of bought goods 100 and unemployed occupation
				.willReturn(WireMock.aResponse()));
						// TODO: Fix me - add application/json content-type matching
						// TODO: Fix me - add json body matching to person details response of name foo, resource id "1" and discount status STORED

		WireMock.stubFor(WireMock.get("/person/1/discount")
				.willReturn(WireMock.aResponse()));
						// TODO: Fix me - add application/json content-type matching
						// TODO: Fix me - add json body matching to person discount response of name foo and discount rate 10.5
	}

	@Test
	void should_calculate_discount_rate_for_person() {
		ResponseEntity<DiscountResponse> discountResponseEntity =
				new TestRestTemplate().exchange(
						RequestEntity.post("http://localhost:" + port + "/discount")
								.body("TODO: pass in a person object that will match the json stub")
						, DiscountResponse.class);

		then(discountResponseEntity.getStatusCode().value()).isEqualTo(200);
		DiscountResponse body = discountResponseEntity.getBody();
		then(body.personName()).isEqualTo("foo");
		then(body.discountRate()).isEqualTo(10.5);
	}

	private String toJson(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		}
		catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
