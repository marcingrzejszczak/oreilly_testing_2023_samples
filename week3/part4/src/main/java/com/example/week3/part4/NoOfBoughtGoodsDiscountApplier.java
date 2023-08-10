package com.example.week3.part4;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NoOfBoughtGoodsDiscountApplier implements DiscountApplier {
	private static final Logger log = Logger.getLogger(NoOfBoughtGoodsDiscountApplier.class.getName());

	static final int THRESHOLD = 5;

	static final double DISCOUNT_RATE = 5D;

	@Override
	public double getDiscountRate(Person person) {
		log.log(Level.INFO, "Calculating number of goods discount");
		if (person.getNumberOfBoughtGoods() <= THRESHOLD) {
			return 0D;
		}
		return DISCOUNT_RATE;
	}
}
