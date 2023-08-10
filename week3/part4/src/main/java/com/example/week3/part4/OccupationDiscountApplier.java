package com.example.week3.part4;

import java.util.logging.Level;
import java.util.logging.Logger;

public class OccupationDiscountApplier implements DiscountApplier {
	private static final Logger log = Logger.getLogger(OccupationDiscountApplier.class.getName());

	static final double DISCOUNT_RATE = 10D;

	@Override
	public double getDiscountRate(Person person) {
		log.log(Level.INFO, "Calculating occupation discount");
		// Simulates calculation from a database
		return DISCOUNT_RATE;
	}

}
