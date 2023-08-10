package com.example.week1.part2.done;

public class NameDiscountApplier implements DiscountApplier {

	static final int THRESHOLD = 3;

	static final double DISCOUNT_RATE = 8D;

	@Override
	public double getDiscountRate(Person person) {
		System.out.println("Calculating name discount");
		if (person.getName().length() < THRESHOLD) { // most likely a prank!
			return 0D;
		}
		return DISCOUNT_RATE;
	}
}
