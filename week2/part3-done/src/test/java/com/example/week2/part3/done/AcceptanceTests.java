package com.example.week2.part3.done;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.BDDAssertions.then;

@WireMockTest(httpPort = 12345)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Week2Part3.class, properties = "person.url=http://localhost:12345")
class AcceptanceTests {

	static final Person UNEMPLOYED_FOO_WITH_A_LOT_OF_GOODS = new Person("foo", 100, Occupation.UNEMPLOYED);

	static final String RESOURCE_ID = "1";

	@LocalServerPort
	int port;
	
	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	void setupWireMock() {
		WireMock.stubFor(WireMock.post("/person/foo")
				.withHeader("Content-Type", WireMock.equalTo("application/json"))
				.withRequestBody(WireMock.equalToJson(toJson(UNEMPLOYED_FOO_WITH_A_LOT_OF_GOODS)))
				.willReturn(WireMock.aResponse()
						.withHeader("Content-Type", "application/json")
						.withBody(toJson(new PersonDetailsResponse(UNEMPLOYED_FOO_WITH_A_LOT_OF_GOODS.getName(), RESOURCE_ID, PersonDetailsResponse.Status.STORED)))));

		WireMock.stubFor(WireMock.get("/person/" + RESOURCE_ID + "/discount")
				.willReturn(WireMock.aResponse()
						.withHeader("Content-Type", "application/json")
						.withBody(toJson(new PersonDiscountResponse("foo", 10.5)))));
	}

	@Test
	void should_calculate_discount_rate_for_person() {
		ResponseEntity<DiscountResponse> discountResponseEntity =
				new TestRestTemplate().exchange(
						RequestEntity.post("http://localhost:" + port + "/discount")
								.body(UNEMPLOYED_FOO_WITH_A_LOT_OF_GOODS)
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
