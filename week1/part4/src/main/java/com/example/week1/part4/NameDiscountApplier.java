package com.example.week1.part4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameDiscountApplier implements DiscountApplier {

	private static final Logger log = LoggerFactory.getLogger(NameDiscountApplier.class);

	static final int THRESHOLD = 3;

	static final double DISCOUNT_RATE = 8D;

	private final MessageSender messageSender;

	public NameDiscountApplier(MessageSender messageSender) {
		this.messageSender = messageSender;
	}

	@Override
	public Discount getDiscountRate(Person person) {
		if (person == null) {
			throw new InvalidPersonException("Person can't be null");
		}
		log.info("Calculating name discount");
		Latency.simulateRandomLatency();
		double rate = rate(person);
		messageSender.sendMessage(new Message(EventType.DISCOUNT_CALCULATED));
		return new Discount(getName(), rate);
	}

	@Override
	public String getName() {
		return "name";
	}

	private double rate(Person person) {
		if (person.getName().length() < THRESHOLD) { // most likely a prank!
			return 0D;
		}
		return DISCOUNT_RATE;
	}
}
