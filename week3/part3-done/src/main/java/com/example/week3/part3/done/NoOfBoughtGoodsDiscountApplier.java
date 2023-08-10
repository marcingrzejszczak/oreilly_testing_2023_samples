package com.example.week3.part3.done;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NoOfBoughtGoodsDiscountApplier implements DiscountApplier {
	private static final Logger log = Logger.getLogger(NoOfBoughtGoodsDiscountApplier.class.getName());

	// This should be configurable
	static int THRESHOLD = 5;

	static double DISCOUNT_RATE = 5D;

	@Override
	public double getDiscountRate(Person person) {
		log.log(Level.INFO, "Calculating number of goods discount");
		if (person.getNumberOfBoughtGoods() == null || person.getNumberOfBoughtGoods() < THRESHOLD) {
			return 0D;
		}
		return DISCOUNT_RATE;
	}
}
