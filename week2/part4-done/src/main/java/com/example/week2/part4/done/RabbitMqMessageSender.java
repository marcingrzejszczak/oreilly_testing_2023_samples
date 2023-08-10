package com.example.week2.part4.done;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RabbitMqMessageSender implements MessageSender {

	private static final Logger log = LoggerFactory.getLogger(RabbitMqMessageSender.class);

	private final String outputQueueName;

	private final ConnectionFactory factory;

	private final ObjectMapper objectMapper;

	public RabbitMqMessageSender(String outputQueueName, ConnectionFactory factory, ObjectMapper objectMapper) {
		this.outputQueueName = outputQueueName;
		this.factory = factory;
		this.objectMapper = objectMapper;
	}

	@Override
	public void sendMessage(Object message) {
		try (Connection connection = factory.newConnection();
			 Channel channel = connection.createChannel()) {
			channel.queueDeclare(outputQueueName, false,
					false, false, null);
			channel.basicPublish("", outputQueueName,
					null, objectMapper.writeValueAsBytes(message));
			log.info("Sent message [{}] to RabbitMQ queue [{}]", message, outputQueueName);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
