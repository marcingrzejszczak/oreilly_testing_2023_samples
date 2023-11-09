package com.example.week2.part4;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AcceptanceTests {

	@Nested
	class UnitTests {
		// TODO: Bonus
		@Test
		void should_throw_exception_when_not_enough_arguments_were_passed() {

		}
	}

	@Nested
			// TODO: Fix me - missing interface implementation with helper methods
	class IntegrationTests {


		int goodsThreshold = 5;
		double goodsDiscountRate = 5D;
		int nameThreshold = 3;
		double nameDiscountRate = 8D;

		@Test
		void should_calculate_maximum_discount() {
			String inputMessage = null; // TODO: Fix me - read mrSmith.json
			String outputMessage = null; // TODO: Fix me - read mrSmithWithDiscountRate.json
			String inputQueue = "inputQueue";
			String outputQueue = "outputQueue";
			// TODO: Fix me - before tests a message should be sent out

			// TODO: Connection factories can't be null
			new Week2Part4(goodsThreshold, goodsDiscountRate, nameThreshold, nameDiscountRate, inputQueue, outputQueue, "events", null, null, new ObjectMapper()) {
				@Override
				RabbitMqMessageListener getMessageListener(String inputQueueName, ConnectionFactory inputConnectionFactory, ObjectMapper objectMapper) {
					return new RabbitMqMessageListener(inputQueueName, inputConnectionFactory, objectMapper); // TODO: Fix me - use the asserting listener
				}
			}.calculateDiscount();

			Awaitility.await()
					.untilAsserted(() -> {
						// TODO: Fix me - check if message was properly sent to broker
					});
		}

	}
}
