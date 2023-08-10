package com.example.week2.part4.done;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.BDDAssertions.then;

@Testcontainers
interface RabbitmqTesting {

	@Container
	GenericContainer rabbitmq =
			new GenericContainer<>("rabbitmq:3.8.3-management")
					.withExposedPorts(5672, 15672);


	default void givenAMessageSentToBroker(String message, String queue) throws IOException, TimeoutException {
		ConnectionFactory factory = connectionFactory();
		try (Connection connection = factory.newConnection();
			 Channel channel = connection.createChannel()) {
			channel.queueDeclare(queue,
					false, false, false, null);
			channel.basicPublish("", queue, null, message.getBytes());
		}
	}

	default void thenMessageWasProperlySentToBroker(String message, String outputQueue) throws Exception {
		ConnectionFactory factory = connectionFactory();
		try (Connection connection = factory.newConnection();
			 Channel channel = connection.createChannel()) {
			GetResponse response = pollForMessage(channel, outputQueue);
			then(response).isNotNull();
			// IMPORTANT: We're using org.skyscreamer:jsonassert to compare JSONs
			JSONAssert.assertEquals(new String(message.getBytes()), new String(response.getBody()), true);
		}
	}

	default String readFile(String classpathPath) {
		try {
			return Files.readString(Path.of(RabbitmqTesting.class.getResource(classpathPath).toURI()));
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static GetResponse pollForMessage(Channel channel, String outputQueue) throws IOException {
		return channel.basicGet(outputQueue, true);
	}

	default ConnectionFactory connectionFactory() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(rabbitmq.getHost());
		factory.setPort(rabbitmq.getMappedPort(5672));
		return factory;
	}
}
