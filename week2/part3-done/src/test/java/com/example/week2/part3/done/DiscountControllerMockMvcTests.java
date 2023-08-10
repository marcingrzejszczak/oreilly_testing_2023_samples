package com.example.week2.part3.done;

import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
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
						new Person("foo", 100, Occupation.UNEMPLOYED))))
				.andExpect(MockMvcResultMatchers.status().is(200))
				.andExpect(MockMvcResultMatchers.jsonPath("$.personName",
						CoreMatchers.equalTo("foo")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.discountRate",
						CoreMatchers.equalTo(10.0)));
	}

	// Error scenario - Validation handling
	@Test
	void should_fail_for_empty_name() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/discount")
						.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(
						new Person("", 100, Occupation.UNEMPLOYED))))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
	}

	// Error scenario - custom exception handling
	@Test
	void should_fail_for_no_goods() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/discount")
						.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(
						new Person("foo", 0, Occupation.UNEMPLOYED))))
				.andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.person.name",
						CoreMatchers.equalTo("foo")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.additionalMessage",
						CoreMatchers.equalTo("We can't apply discounts to people who didn't buy any goods")));
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
