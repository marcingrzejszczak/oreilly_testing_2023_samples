package com.example.week3.part1.done.applier;

import com.example.week3.part1.done.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class NoOfBoughtGoodsDiscountApplier implements DiscountApplier {

	private static final Logger log = LoggerFactory.getLogger(NoOfBoughtGoodsDiscountApplier.class);

	private static final int THRESHOLD = 5;

	private static final double DISCOUNT_RATE = 5D;

	@Override
	public double getDiscountRate(Person person) {
		log.info("Calculating number of goods discount");
		if (person.getNumberOfBoughtGoods() < THRESHOLD) {
			return 0D;
		}
		return DISCOUNT_RATE;
	}
}
