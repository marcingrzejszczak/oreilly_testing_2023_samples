package com.example.week2.part2.done;

public class NameDiscountApplier implements DiscountApplier {

	private final int threshold;

	private final double discountRate;

	public NameDiscountApplier(int threshold, double discountRate) {
		this.threshold = threshold;
		this.discountRate = discountRate;
	}

	@Override
	public double getDiscountRate(Person person) {
		System.out.println("Calculating name discount for threshold [" + threshold + "] and rate [" + discountRate + "]");
		if (person.getName().length() < threshold) { // most likely a prank!
			return 0D;
		}
		return discountRate;
	}
}
