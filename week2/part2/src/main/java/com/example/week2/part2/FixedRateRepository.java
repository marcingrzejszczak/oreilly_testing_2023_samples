package com.example.week2.part2;

public class FixedRateRepository implements RateRepository {
	private static final double FIXED_RATE = 10D;

	@Override
	public double getDiscountRate(Occupation occupation) {
		System.out.println("Returning fixed rate [" + FIXED_RATE + "]");
		return FIXED_RATE;
	}
}
