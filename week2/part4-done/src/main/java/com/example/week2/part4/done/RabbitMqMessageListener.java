package com.example.week2.part4.done;

import java.io.IOException;
import java.util.function.Consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RabbitMqMessageListener implements MessageListener<Person> {

	private static final Logger log = LoggerFactory.getLogger(RabbitMqMessageListener.class);

	private final String inputQueueName;

	private final ConnectionFactory factory;

	private final ObjectMapper objectMapper;

	public RabbitMqMessageListener(String inputQueueName, ConnectionFactory factory, ObjectMapper objectMapper) {
		this.inputQueueName = inputQueueName;
		this.factory = factory;
		this.objectMapper = objectMapper;
	}

	@Override
	public void pollForMessage(Consumer<Person> doForMessage) {
		try (Connection connection = factory.newConnection();
			 Channel channel = connection.createChannel()) {
			channel.queueDeclare(inputQueueName, false,
					false, false, null);
			while (pollingCondition()) {
				GetResponse response = channel.basicGet(inputQueueName, true);
				if (response != null) {
					Person person = readMessage(response);
					log.info("Received person: [{}]", person);
					doForMessage.accept(person);
				}
				else {
					log.trace("No message in the queue");
				}
			}
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	boolean pollingCondition() {
		return true;
	}

	Person readMessage(GetResponse response) throws IOException {
		return objectMapper.readerFor(Person.class).readValue(response.getBody());
	}

}
