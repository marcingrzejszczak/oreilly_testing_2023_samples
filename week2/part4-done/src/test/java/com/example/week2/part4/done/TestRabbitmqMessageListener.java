package com.example.week2.part4.done;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

class TestRabbitmqMessageListener extends RabbitMqMessageListener {

	AtomicReference<Person> message = new AtomicReference<>();

	public TestRabbitmqMessageListener(String inputQueueName, ConnectionFactory factory, ObjectMapper objectMapper) {
		super(inputQueueName, factory, objectMapper);
	}

	@Override
	Person readMessage(GetResponse response) throws IOException {
		Person readMessage = super.readMessage(response);
		message.set(readMessage);
		return readMessage;
	}

	@Override
	boolean pollingCondition() {
		return message.get() == null;
	}
}
