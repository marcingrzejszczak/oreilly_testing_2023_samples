package com.example.week2.part4.done;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class RabbitmqMessageSenderTests {

	@Nested
	class UnitTests {

		@Test
		void should_throw_exception_when_issues_with_connection() throws Exception {
			ConnectionFactory connectionFactory = mock();
			given(connectionFactory.newConnection())
					.willThrow(new IOException("BOOM!"));

			thenThrownBy(() -> new RabbitMqMessageSender("output", connectionFactory, null)
					.sendMessage(null))
					.hasRootCauseInstanceOf(IOException.class)
					.hasMessageContaining("BOOM!");
		}
	}

	@Nested
	class IntegrationTests implements RabbitmqTesting {

		String outputMessage;

		String outputQueue = "output";

		RabbitMqMessageSender sender;

		@BeforeEach
		void setup() throws Exception {
			sender = new RabbitMqMessageSender(outputQueue, connectionFactory(), new ObjectMapper());
			outputMessage = readFile("/json/calculatedEvent.json");
		}

		@Test
		void should_send_message_to_a_queue_in_json_format() {
			sender.sendMessage(new Message(EventType.DISCOUNT_CALCULATED));

			Awaitility.await()
					.untilAsserted(() -> thenMessageWasProperlySentToBroker(outputMessage, outputQueue));
		}
	}

}
