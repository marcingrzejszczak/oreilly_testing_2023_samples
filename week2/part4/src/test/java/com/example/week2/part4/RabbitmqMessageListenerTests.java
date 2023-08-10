package com.example.week2.part4;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RabbitmqMessageListenerTests {

	@Nested
	class UnitTests {

		// TODO: Bonus
		@Test
		void should_throw_exception_when_issues_with_connection()
				throws IOException, TimeoutException {

		}
	}

	@Nested
	class IntegrationTests implements RabbitmqTesting {

		String inputMessage;

		String inputQueue = "input";

		MessageListener<Person> listener;

		@BeforeEach
		void setup() {
			listener = new RabbitMqMessageListener(inputQueue, connectionFactory(), new ObjectMapper()); // TODO: Fix me - use a test type, otherwise the test will never stop!
			inputMessage = null; // TODO: Fix me - read the json/mrSmith.json file
		}

		@Test
		void should_receive_a_message_from_broker() throws IOException, TimeoutException {
			givenAMessageSentToBroker(inputMessage, inputQueue);

			listener.pollForMessage(person -> { });

			// TODO: Fix me - add assertion. If you use a test message listener it will have an atomic reference to the message. Check if it's equal to Mr Smith Person object
		}
	}
}
