package com.example.week2.part3;

import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

// TODO: Fix me - add missing web mvc test slice
@ContextConfiguration(classes = DiscountControllerMockMvcTests.MyTestConfiguration.class)
class DiscountControllerMockMvcTests {

	@Autowired MockMvc mockMvc;

	@Autowired ObjectMapper objectMapper;

	// Successful scenario - serialization + deserialization
	@Test
	void should_calculate_discount_rate_for_person() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/discount")
						.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(
						new Person("foo", 100, Occupation.UNEMPLOYED))));
				// TODO: Fix me - Expect status 200
				// TODO: Fix me - Expect JsonPath $.personName to be equal to foo
				// TODO: Fix me - Expect JsonPath $.discountRate to be equal to 10.0;
	}

	// Error scenario - Validation handling
	@Test
	void should_fail_for_empty_name() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/discount")
						.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(
						new Person("", 100, Occupation.UNEMPLOYED))));
				// TODO: Fix me - Expect status Bad Request
	}

	// Error scenario - custom exception handling
	@Test
	void should_fail_for_no_goods() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/discount")
						.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(
						new Person("foo", 0, Occupation.UNEMPLOYED))));
				// TODO: Fix me - Expect status Bad Request
				// TODO: Fix me - Expect JsonPath $.person.name to be equal to foo
				// TODO: Fix me - Expect JsonPath $.additionalMessage to be properly set (look at DiscountControllerExceptionHandler)
	}

	@TestConfiguration(proxyBeanMethods = false)
	static class MyTestConfiguration {
		@Bean
		DiscountCalculator discountCalculator() {
			return new DiscountCalculator(Collections.emptyList()) {
				@Override
				public void calculateTotalDiscountRate(Person person) {
					person.setDiscountRate(10);
				}
			};
		}
	}
}
