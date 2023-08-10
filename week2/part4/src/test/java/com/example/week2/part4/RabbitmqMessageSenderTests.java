package com.example.week2.part4;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RabbitmqMessageSenderTests {

	@Nested
	class UnitTests {

		// TODO: Bonus
		@Test
		void should_throw_exception_when_issues_with_connection() throws Exception {

		}
	}

	@Nested
	class IntegrationTests implements RabbitmqTesting {

		String outputMessage; // TODO: Fix me - read the json/calculatedEvent.json file

		String outputQueue = "output";

		RabbitMqMessageSender sender;

		@BeforeEach
		void setup() {
			sender = null; // TODO: Create the sender, check RabbitmqTesting for helper methods to create this instance
		}

		@Test
		void should_send_message_to_a_queue_in_json_format() {
			sender.sendMessage(new Message(EventType.DISCOUNT_CALCULATED));

			Awaitility.await()
					.untilAsserted(() -> {
						// TODO: Fix me - assert that the message was sent to the broker - check RabbitmqTesting for helper methods
					});
		}
	}

}
