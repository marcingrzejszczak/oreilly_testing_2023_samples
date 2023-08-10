package com.example.week2.part4;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Week2Part4 {

	private static final Logger log = LoggerFactory.getLogger(Week2Part4.class);

	private final int goodsThreshold;

	private final double goodsDiscountRate;

	private final int nameThreshold;

	private final double nameDiscountRate;

	private final String personInputQueueName;

	private final String personOutputQueueName;

	private final String eventOutputQueueName;

	private final ConnectionFactory inputConnectionFactory;

	private final ConnectionFactory outputConnectionFactory;

	private final ObjectMapper objectMapper;

	public Week2Part4(int goodsThreshold, double goodsDiscountRate, int nameThreshold, double nameDiscountRate, String personInputQueueName, String personOutputQueueName, String eventOutputQueueName, ConnectionFactory inputConnectionFactory, ConnectionFactory outputConnectionFactory, ObjectMapper objectMapper) {
		this.goodsThreshold = goodsThreshold;
		this.goodsDiscountRate = goodsDiscountRate;
		this.nameThreshold = nameThreshold;
		this.nameDiscountRate = nameDiscountRate;
		this.personInputQueueName = personInputQueueName;
		this.personOutputQueueName = personOutputQueueName;
		this.eventOutputQueueName = eventOutputQueueName;
		this.inputConnectionFactory = inputConnectionFactory;
		this.outputConnectionFactory = outputConnectionFactory;
		this.objectMapper = objectMapper;
	}

	void calculateDiscount() {
		MessageSender personMessageSender = new RabbitMqMessageSender(personOutputQueueName, outputConnectionFactory, objectMapper);
		MessageSender eventMessageSender = new RabbitMqMessageSender(eventOutputQueueName, outputConnectionFactory, objectMapper);
		MessageListener<Person> messageListener = getMessageListener(personInputQueueName, inputConnectionFactory, objectMapper);
		List<DiscountApplier> appliers = Arrays.asList(new OccupationDiscountApplier(new FixedRateRepository(), eventMessageSender), new NoOfBoughtGoodsDiscountApplier(goodsThreshold, goodsDiscountRate, eventMessageSender), new NameDiscountApplier(nameThreshold, nameDiscountRate, eventMessageSender));
		DiscountCalculator discountCalculator = new DiscountCalculator(personMessageSender, appliers);
		log.info("Will poll for a message with a Person on input queue [{}]", personInputQueueName);
		messageListener.pollForMessage(discountCalculator::calculateTotalDiscountRate);
	}

	RabbitMqMessageListener getMessageListener(String inputQueueName, ConnectionFactory inputConnectionFactory, ObjectMapper objectMapper) {
		return new RabbitMqMessageListener(inputQueueName, inputConnectionFactory, objectMapper);
	}

	private static ConnectionFactory connectionFactory() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		factory.setPort(5672);
		return factory;
	}

	// Arguments
	// inputQueue outputQueue
	// personInput personOutput

	// Person received from a RabbitMq queue
	// name noOfBoughtGoods Occupation
	// foo 15 EMPLOYED
	// bar 5 UNEMPLOYED
	public static void main(String[] args) {
		if (args.length != 2) {
			throw new IllegalArgumentException("Wrong number of arguments " + Arrays.toString(args) + ". There must be exactly 2 arguments");
		}
		String inputQueueName = args[0];
		String outputQueueName = args[1];
		// For simplicity's sake we're fixing most values
		new Week2Part4(5, 5D, 3, 8D, inputQueueName, outputQueueName, "events", connectionFactory(), connectionFactory(), new ObjectMapper()).calculateDiscount();
		System.exit(0);
	}
}
