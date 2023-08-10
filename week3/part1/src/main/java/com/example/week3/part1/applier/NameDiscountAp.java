package com.example.week3.part1.applier;

import com.example.week3.part1.model.Person;

public class NameDiscountAp implements DiscountApplier {

	static final int THRESHOLD = 3;

	static final double DISCOUNT_RATE = 8D;

	private Person p;

	@Override
	public double getDiscountRate(Person person) {
		System.out.println("Calculating name discount");
		if (person.getName().length() < THRESHOLD) { // most likely a prank!
			return 0D;
		}
		return DISCOUNT_RATE;
	}
}
