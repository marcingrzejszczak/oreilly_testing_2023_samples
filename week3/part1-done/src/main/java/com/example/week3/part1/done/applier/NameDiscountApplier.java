package com.example.week3.part1.done.applier;

import com.example.week3.part1.done.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class NameDiscountApplier implements DiscountApplier {

	private static final Logger log = LoggerFactory.getLogger(NameDiscountApplier.class);

	private static final int THRESHOLD = 3;

	private static final double DISCOUNT_RATE = 8D;

	@Override
	public double getDiscountRate(Person person) {
		log.info("Calculating name discount");
		if (person.getName().length() < THRESHOLD) { // most likely a prank!
			return 0D;
		}
		return DISCOUNT_RATE;
	}
}
