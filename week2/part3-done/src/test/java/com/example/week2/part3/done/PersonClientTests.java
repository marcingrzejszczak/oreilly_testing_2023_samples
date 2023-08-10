package com.example.week2.part3.done;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Fault;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class PersonClientTests {

	private static final String EXPECTED_REFERENCE_ID = "1234";

	private static final String EXPECTED_PERSON_NAME = "smith";

	private static final PersonDetailsResponse.Status EXPECTED_STORED_RESPONSE_STATUS = PersonDetailsResponse.Status.STORED;

	@RegisterExtension
	static WireMockExtension wm = WireMockExtension.newInstance()
			.options(wireMockConfig().dynamicPort())
			.configureStaticDsl(true)
			.build();

	PersonClient personClient = new PersonClient(restTemplate(), "http://localhost:" + wm.getPort());

	Person mrSmith = mrSmith();

	private RestTemplate restTemplate() {
		return new DiscountConfiguration.ClientConfiguration().personRestTemplate(500, 500);
	}

	@Nested
	class PersonUpdateTests {
		@Test
		void should_return_person_details_response_when_not_in_manual_processing() {
			WireMock.stubFor(WireMock.post("/person/smith")
					.withHeader("Content-Type", WireMock.equalTo("application/json"))
					.withRequestBody(WireMock.equalToJson(toJson(mrSmith)))
					.willReturn(WireMock.aResponse()
							.withHeader("Content-Type", "application/json")
							.withBody(toJson(storedProcessingResponse()))));

			PersonDetailsResponse response = personClient.updatePersonDetails(mrSmith);

			// Will assert everything and throw exception at the end
			SoftAssertions.assertSoftly(softAssertions -> {
				softAssertions.assertThat(response.name()).isEqualTo(EXPECTED_PERSON_NAME);
				softAssertions.assertThat(response.resourceId()).isEqualTo(EXPECTED_REFERENCE_ID);
				softAssertions.assertThat(response.status()).isEqualTo(EXPECTED_STORED_RESPONSE_STATUS);
			});
		}

		@Test
		void should_fail_with_manual_processing_exception_for_manual_processing_status() {
			WireMock.stubFor(WireMock.post("/person/smith")
					.withHeader("Content-Type", WireMock.equalTo("application/json"))
					.withRequestBody(WireMock.equalToJson(toJson(mrSmith)))
					.willReturn(WireMock.aResponse()
							.withHeader("Content-Type", "application/json")
							.withBody(toJson(manualProcessingResponse()))));

			thenThrownBy(() -> personClient.updatePersonDetails(mrSmith))
					.isInstanceOf(PersonInManualProcessingException.class);
		}

		@Test
		void should_fail_with_timeout_from_server() {
			WireMock.stubFor(WireMock.post("/person/smith")
					.withHeader("Content-Type", WireMock.equalTo("application/json"))
					.withRequestBody(WireMock.equalToJson(toJson(mrSmith)))
					.willReturn(WireMock.aResponse().withFixedDelay(1000)));

			thenThrownBy(() -> personClient.updatePersonDetails(mrSmith))
					.isInstanceOf(PersonNotStoredException.class)
					.hasRootCauseInstanceOf(SocketTimeoutException.class);
		}

		@Test
		void should_fail_with_connection_reset_by_peer_from_server() {
			WireMock.stubFor(WireMock.post("/person/smith")
					.willReturn(WireMock.aResponse().withFault(Fault.CONNECTION_RESET_BY_PEER)));

			thenThrownBy(() -> personClient.updatePersonDetails(mrSmith))
					.isInstanceOf(PersonNotStoredException.class)
					.hasRootCauseInstanceOf(SocketException.class);
		}

		@Test
		void should_fail_with_empty_response_from_server() {
			WireMock.stubFor(WireMock.post("/person/smith")
					.willReturn(WireMock.aResponse().withFault(Fault.EMPTY_RESPONSE)));

			thenThrownBy(() -> personClient.updatePersonDetails(mrSmith))
					.isInstanceOf(PersonNotStoredException.class)
					.hasRootCauseInstanceOf(SocketException.class);
		}

		@Test
		void should_fail_with_malformed_response_from_server() {
			WireMock.stubFor(WireMock.post("/person/smith")
					.willReturn(WireMock.aResponse().withFault(Fault.MALFORMED_RESPONSE_CHUNK)));

			thenThrownBy(() -> personClient.updatePersonDetails(mrSmith))
					.isInstanceOf(PersonNotStoredException.class)
					.hasRootCauseInstanceOf(IOException.class);
		}

		@Test
		void should_fail_with_random_data_then_close_from_server() {
			WireMock.stubFor(WireMock.post("/person/smith")
					.willReturn(WireMock.aResponse().withFault(Fault.RANDOM_DATA_THEN_CLOSE)));

			thenThrownBy(() -> personClient.updatePersonDetails(mrSmith))
					.isInstanceOf(PersonNotStoredException.class);
		}

		private static PersonDetailsResponse storedProcessingResponse() {
			return new PersonDetailsResponse(EXPECTED_PERSON_NAME, EXPECTED_REFERENCE_ID, EXPECTED_STORED_RESPONSE_STATUS);
		}

		private static PersonDetailsResponse manualProcessingResponse() {
			return new PersonDetailsResponse(EXPECTED_PERSON_NAME, EXPECTED_REFERENCE_ID, PersonDetailsResponse.Status.MANUAL_PROCESSING);
		}
	}

	@Nested
	class PersonGetDiscountTests {
		@Test
		void should_return_discount_for_person() {
			WireMock.stubFor(WireMock.get("/person/1/discount")
					.willReturn(WireMock.aResponse()
							.withHeader("Content-Type", "application/json")
							.withBody(toJson(new PersonDiscountResponse("smith", 10.5)))));

			double response = personClient.getDiscount("1");

			then(response).isEqualTo(10.5);
		}

		@Test
		void should_return_no_discount_with_timeout_from_server() {
			WireMock.stubFor(WireMock.get("/person/1/discount")
					.withHeader("Content-Type", WireMock.equalTo("application/json"))
					.willReturn(WireMock.aResponse().withFixedDelay(1000)));

			double response = personClient.getDiscount("1");

			then(response).isZero();
		}

		@Test
		void should_return_no_discount_with_connection_reset_by_peer_from_server() {
			WireMock.stubFor(WireMock.get("/person/1/discount")
					.willReturn(WireMock.aResponse().withFault(Fault.CONNECTION_RESET_BY_PEER)));

			double response = personClient.getDiscount("1");

			then(response).isZero();
		}

		@Test
		void should_return_no_discount_with_empty_response_from_server() {
			WireMock.stubFor(WireMock.get("/person/1/discount")
					.willReturn(WireMock.aResponse().withFault(Fault.EMPTY_RESPONSE)));

			double response = personClient.getDiscount("1");

			then(response).isZero();
		}

		@Test
		void should_return_no_discount_with_malformed_response_from_server() {
			WireMock.stubFor(WireMock.get("/person/1/discount")
					.willReturn(WireMock.aResponse().withFault(Fault.MALFORMED_RESPONSE_CHUNK)));

			double response = personClient.getDiscount("1");

			then(response).isZero();
		}

		@Test
		void should_return_no_discount_with_random_data_then_close_from_server() {
			WireMock.stubFor(WireMock.get("/person/1/discount")
					.willReturn(WireMock.aResponse().withFault(Fault.RANDOM_DATA_THEN_CLOSE)));

			double response = personClient.getDiscount("1");

			then(response).isZero();
		}
	}

	private static String toJson(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		}
		catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	private static Person mrSmith() {
		return new Person(EXPECTED_PERSON_NAME, 10, Occupation.UNEMPLOYED);
	}

}
