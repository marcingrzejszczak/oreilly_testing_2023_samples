package com.example.week2.part4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FixedRateRepository implements RateRepository {

	private static final Logger log = LoggerFactory.getLogger(FixedRateRepository.class);

	private static final double FIXED_RATE = 10D;

	@Override
	public double getDiscountRate(Occupation occupation) {
		log.info("Returning fixed rate [" + FIXED_RATE + "]");
		return FIXED_RATE;
	}
}

