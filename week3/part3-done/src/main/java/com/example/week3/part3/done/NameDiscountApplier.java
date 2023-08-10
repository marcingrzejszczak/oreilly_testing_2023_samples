package com.example.week3.part3.done;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NameDiscountApplier implements DiscountApplier {

	private static final Logger log = Logger.getLogger(NameDiscountApplier.class.getName());

	static final int THRESHOLD = 3;

	static final double DISCOUNT_RATE = 8D;

	@Override
	public double getDiscountRate(Person person) {
		log.log(Level.INFO, "Calculating name discount");
		if (person.getName() == null || person.getName().length() < THRESHOLD) { // most likely a prank!
			return 0D;
		}
		return DISCOUNT_RATE;
	}
}
