package com.example.week1.part4.done;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoOfBoughtGoodsDiscountApplier implements DiscountApplier {

	private static final Logger log = LoggerFactory.getLogger(NoOfBoughtGoodsDiscountApplier.class);

	// This should be configurable
	static int THRESHOLD = 5;

	static double DISCOUNT_RATE = 5D;

	private final MessageSender messageSender;

	public NoOfBoughtGoodsDiscountApplier(MessageSender messageSender) {
		this.messageSender = messageSender;
	}

	@Override
	public Discount getDiscountRate(Person person) {
		if (person == null) {
			throw new InvalidPersonException("Person can't be null");
		}
		messageSender.sendMessage(new Message(EventType.DISCOUNT_CALCULATION_RECEIVED));
		log.info("Calculating number of goods discount");
		Latency.simulateRandomLatency();
		double rate = rate(person);
		// THIS EVENT MUST BE CALLED AFTER RECEIVED!!
		messageSender.sendMessage(new Message(EventType.DISCOUNT_CALCULATED));
		return new Discount(getName(), rate);
	}

	@Override
	public String getName() {
		return "no of goods";
	}

	private static double rate(Person person) {
		if (person.getNumberOfBoughtGoods() < THRESHOLD) {
			return 0D;
		}
		return DISCOUNT_RATE;
	}
}
