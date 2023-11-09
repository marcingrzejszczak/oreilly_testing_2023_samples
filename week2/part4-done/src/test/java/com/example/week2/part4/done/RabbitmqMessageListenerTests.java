package com.example.week2.part4.done;

import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.assertj.core.api.BDDAssertions;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;

class RabbitmqMessageListenerTests {

	@Nested
	class UnitTests {

		@Test
		void should_throw_exception_when_issues_with_connection()
				throws IOException, TimeoutException {
			ConnectionFactory connectionFactory = mock();
			BDDMockito.given(connectionFactory.newConnection())
					.willThrow(new IOException("BOOM!"));

			BDDAssertions.thenThrownBy(() ->
							new RabbitMqMessageListener("input", connectionFactory, null)
									.pollForMessage(person -> { }))
					.hasRootCauseInstanceOf(IOException.class)
					.hasMessageContaining("BOOM!");
		}
	}

	@Nested
	class IntegrationTests implements RabbitmqTesting {

		String inputMessage;

		String inputQueue = "input";

		TestRabbitmqMessageListener listener;

		@BeforeEach
		void setup() throws Exception {
			listener = new TestRabbitmqMessageListener(inputQueue, connectionFactory(), new ObjectMapper());
			inputMessage = readFile("/json/mrSmith.json");
		}

		@Test
		void should_receive_a_message_from_broker() throws IOException, TimeoutException {
			givenAMessageSentToBroker(inputMessage, inputQueue);


			Awaitility.await()
					.atMost(200, TimeUnit.MILLISECONDS)
					.untilAsserted(() -> {
						listener.pollForMessage(person -> { });

						// TODO: Update the slides
						BDDAssertions.then(listener.message.get()).isEqualTo(new Person("smith", 100, Occupation.EMPLOYED));
					});
		}
	}
}
