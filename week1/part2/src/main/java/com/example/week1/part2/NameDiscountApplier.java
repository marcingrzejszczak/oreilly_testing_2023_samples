package com.example.week1.part2;

public class NameDiscountApplier implements DiscountApplier {

	@Override
	public double getDiscountRate(Person person) {
		System.out.println("Calculating name discount");
		if (person.getName().length() < 3) { // most likely a prank!
			return 0D;
		}
		return 8D;
	}
}
