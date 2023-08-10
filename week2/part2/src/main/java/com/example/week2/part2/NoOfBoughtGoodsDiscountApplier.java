package com.example.week2.part2;

public class NoOfBoughtGoodsDiscountApplier implements DiscountApplier {

	private final int threshold;

	private final double discountRate;

	public NoOfBoughtGoodsDiscountApplier(int threshold, double discountRate) {
		this.threshold = threshold;
		this.discountRate = discountRate;
	}

	@Override
	public double getDiscountRate(Person person) {
		System.out.println("Calculating number of goods discount for threshold [" + threshold + "] and rate [" + discountRate + "]");
		if (person.getNumberOfBoughtGoods() < threshold) {
			return 0D;
		}
		return discountRate;
	}
}
