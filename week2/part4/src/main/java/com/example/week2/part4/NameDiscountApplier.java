package com.example.week2.part4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameDiscountApplier implements DiscountApplier {

	private static final Logger log = LoggerFactory.getLogger(NameDiscountApplier.class);

	private final int nameLengthThreshold;

	private final double discountRate;

	private final MessageSender messageSender;

	public NameDiscountApplier(int nameLengthThreshold, double discountRate, MessageSender messageSender) {
		this.nameLengthThreshold = nameLengthThreshold;
		this.discountRate = discountRate;
		this.messageSender = messageSender;
	}

	@Override
	public void applyDiscount(Person person) {
		log.info("Calculating name discount");
		double nameRate = rate(person);
		person.setDiscountRate(person.getDiscountRate() + nameRate);
		log.info("Name discount [{}]", nameRate);
		messageSender.sendMessage(new Message(EventType.DISCOUNT_CALCULATED));
	}

	private double rate(Person person) {
		if (person.getName().length() < nameLengthThreshold) { // most likely a prank!
			return 0D;
		}
		return discountRate;
	}
}
