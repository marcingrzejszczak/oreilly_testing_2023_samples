package com.example.week2.part5.done;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NameDiscountApplier implements DiscountApplier {

	private static final Logger log = LoggerFactory.getLogger(NameDiscountApplier.class);

	static final int THRESHOLD = 3;

	static final double DISCOUNT_RATE = 8D;

	@Override
	public double getDiscountRate(Person person) {
		log.info("Calculating name discount");
		if (person.getName().length() < THRESHOLD) { // most likely a prank!
			return 0D;
		}
		return DISCOUNT_RATE;
	}
}
