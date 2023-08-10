package com.example.week2.part3;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class PersonClientTests {

	private static final String EXPECTED_REFERENCE_ID = "1234";

	private static final String EXPECTED_PERSON_NAME = "smith";

	private static final PersonDetailsResponse.Status EXPECTED_STORED_RESPONSE_STATUS = PersonDetailsResponse.Status.STORED;

	// TODO: Fix me - register a WireMock JUnit 5 extension with a dynamic port and static dsl
	static WireMockExtension wm;

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
					// TODO: Fix me - add application/json content-type matching
					// TODO: Fix me - add json body matching to json mr smith
					.willReturn(WireMock.aResponse()
							// TODO: Fix me - add application/json content-type matching
							.withBody(toJson(storedProcessingResponse()))));

			PersonDetailsResponse response = personClient.updatePersonDetails(mrSmith);

			// Soft Assertions assert everything and throw exception at the end with the list of all failing assertions
			SoftAssertions.assertSoftly(softAssertions -> {
				// TODO: Fix me - assert that the fields contain proper values
			});
		}

		@Test
		void should_fail_with_manual_processing_exception_for_manual_processing_status() {
			WireMock.stubFor(WireMock.post("/person/smith")
					// TODO: Fix me - add application/json content-type matching
					// TODO: Fix me - add json body matching to json mr smith
					.willReturn(WireMock.aResponse()
							// TODO: Fix me - add application/json content-type matching
							.withBody(toJson(manualProcessingResponse()))));

			// TODO: Fix me - add assertion, PersonInManualProcessingException should be thrown
		}

		@Test
		void should_fail_with_timeout_from_server() {
			WireMock.stubFor(WireMock.post("/person/smith"));
					// TODO: Fix me - add application/json content-type matching
					// TODO: Fix me - add json body matching to json mr smith
					// TODO: Add fixed delay of 1000

			// TODO: Fix me - add assertion, PersonNotStoredException should be thrown + check the root cause
		}

		@Test
		void should_fail_with_connection_reset_by_peer_from_server() {
			WireMock.stubFor(WireMock.post("/person/smith"));
			// TODO: Add Fault.CONNECTION_RESET_BY_PEER fault

			// TODO: Fix me - add assertion, PersonNotStoredException should be thrown + check the root cause
		}

		@Test
		void should_fail_with_empty_response_from_server() {
			WireMock.stubFor(WireMock.post("/person/smith"));
			// TODO: Add Fault.EMPTY_RESPONSE fault;

			// TODO: Fix me - add assertion, PersonNotStoredException should be thrown + check the root cause
		}

		@Test
		void should_fail_with_malformed_response_from_server() {
			WireMock.stubFor(WireMock.post("/person/smith"));
			// TODO: Add Fault.MALFORMED_RESPONSE_CHUNK fault

			// TODO: Fix me - add assertion, PersonNotStoredException should be thrown + check the root cause
		}

		@Test
		void should_fail_with_random_data_then_close_from_server() {
			WireMock.stubFor(WireMock.post("/person/smith"));
			// TODO: Add Fault.RANDOM_DATA_THEN_CLOSE fault

			// TODO: Fix me - add assertion, PersonNotStoredException should be thrown + check the root cause
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
					.willReturn(WireMock.aResponse()));
							// TODO: Fix me - add application/json content-type matching
							// TODO: Fix me - add json body matching to discount response of mr smith with 10.5 discount rate

			double response = personClient.getDiscount("1");

			// TODO: Fix me - add assertion, should be 10.5
		}

		@Test
		void should_return_no_discount_with_timeout_from_server() {
			WireMock.stubFor(WireMock.get("/person/1/discount")
					// TODO: Fix me - add application/json content-type matching
					.willReturn(WireMock.aResponse()));
			// TODO: Add Fixed delay of 1000 millis

			double response = personClient.getDiscount("1");

			// TODO: Fix me - add assertion, should be 0
		}

		@Test
		void should_return_no_discount_with_connection_reset_by_peer_from_server() {
			WireMock.stubFor(WireMock.get("/person/1/discount"));
			// TODO: Add Fault.CONNECTION_RESET_BY_PEER fault

			double response = personClient.getDiscount("1");

			// TODO: Fix me - add assertion, should be 0
		}

		@Test
		void should_return_no_discount_with_empty_response_from_server() {
			WireMock.stubFor(WireMock.get("/person/1/discount"));
			// TODO: Add Fault.EMPTY_RESPONSE fault

			double response = personClient.getDiscount("1");

			// TODO: Fix me - add assertion, , should be 0should be 0
		}

		@Test
		void should_return_no_discount_with_malformed_response_from_server() {
			WireMock.stubFor(WireMock.get("/person/1/discount"));
			// TODO: Add Fault.MALFORMED_RESPONSE_CHUNK fault

			double response = personClient.getDiscount("1");

			// TODO: Fix me - add assertion, should be 0
		}

		@Test
		void should_return_no_discount_with_random_data_then_close_from_server() {
			WireMock.stubFor(WireMock.get("/person/1/discount"));
			// TODO: Add Fault.RANDOM_DATA_THEN_CLOSE fault

			double response = personClient.getDiscount("1");

			// TODO: Fix me - add assertion, should be 0
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
