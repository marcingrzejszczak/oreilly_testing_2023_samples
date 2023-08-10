package com.example.week2.part5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoOfBoughtGoodsDiscountApplier implements DiscountApplier {

	private static final Logger log = LoggerFactory.getLogger(NoOfBoughtGoodsDiscountApplier.class);
	
	// This should be configurable
	static int THRESHOLD = 5;

	static double DISCOUNT_RATE = 5D;

	@Override
	public double getDiscountRate(Person person) {
		log.info("Calculating number of goods discount");
		if (person.getNumberOfBoughtGoods() < THRESHOLD) {
			return 0D;
		}
		return DISCOUNT_RATE;
	}
}
