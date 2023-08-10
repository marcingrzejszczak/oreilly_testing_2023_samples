package com.example.week3.part4.done;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NameDiscountApplier implements DiscountApplier {

	private static final Logger log = Logger.getLogger(NameDiscountApplier.class.getName());

	static final int LOWER_THRESHOLD = 3;

	static final int UPPER_THRESHOLD = 50;

	static final double DISCOUNT_RATE = 8D;

	@Override
	public double getDiscountRate(Person person) {
		log.log(Level.INFO, () -> "Calculating name discount for name " + person.getName());
		if (person.getName().length() <= LOWER_THRESHOLD || person.getName().length() >= UPPER_THRESHOLD) { // most likely a prank!
			return 0D;
		}
		log.log(Level.INFO, () -> person.getName() + " name meets the threshold");
		return DISCOUNT_RATE;
	}

}
