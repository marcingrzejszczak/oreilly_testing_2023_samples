package com.example.week2.part4.done;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoOfBoughtGoodsDiscountApplier implements DiscountApplier {

	private static final Logger log = LoggerFactory.getLogger(NoOfBoughtGoodsDiscountApplier.class);

	private final int goodsThreshold;

	private final double discountRate;

	private final MessageSender messageSender;

	public NoOfBoughtGoodsDiscountApplier(int goodsThreshold, double discountRate, MessageSender messageSender) {
		this.goodsThreshold = goodsThreshold;
		this.discountRate = discountRate;
		this.messageSender = messageSender;
	}

	@Override
	public void applyDiscount(Person person) {
		messageSender.sendMessage(new Message(EventType.DISCOUNT_CALCULATION_RECEIVED));
		log.info("Calculating number of goods discount");
		double rate = rate(person);
		person.setDiscountRate(person.getDiscountRate() + rate);
		log.info("Number of goods discount [{}]", rate);
		// THIS EVENT MUST BE CALLED AFTER RECEIVED!!
		messageSender.sendMessage(new Message(EventType.DISCOUNT_CALCULATED));
	}

	private double rate(Person person) {
		if (person.getNumberOfBoughtGoods() < goodsThreshold) {
			return 0D;
		}
		return discountRate;
	}
}
