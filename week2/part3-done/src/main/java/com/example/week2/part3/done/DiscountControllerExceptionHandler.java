package com.example.week2.part3.done;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class DiscountControllerExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NoBoughtGoodsException.class)
	@ResponseBody
	CustomResponse handleNoBoughtGoodsException(NoBoughtGoodsException ex) {
		return new CustomResponse(ex.getPerson());
	}

	public static class CustomResponse {
		private final Person person;

		private final String additionalMessage;

		@JsonCreator
		public CustomResponse(@JsonProperty("person") Person person) {
			this.person = person;
			this.additionalMessage = "We can't apply discounts to people who didn't buy any goods";
		}

		public Person getPerson() {
			return person;
		}

		public String getAdditionalMessage() {
			return additionalMessage;
		}
	}
}
