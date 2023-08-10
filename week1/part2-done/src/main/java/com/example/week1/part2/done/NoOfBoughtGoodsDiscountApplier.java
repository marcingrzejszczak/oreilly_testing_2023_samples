package com.example.week1.part2.done;

public class NoOfBoughtGoodsDiscountApplier implements DiscountApplier {

	// This should be configurable
	static int THRESHOLD = 5;

	static double DISCOUNT_RATE = 5D;

	@Override
	public double getDiscountRate(Person person) {
		System.out.println("Calculating number of goods discount");
		if (person.getNumberOfBoughtGoods() < THRESHOLD) {
			return 0D;
		}
		return DISCOUNT_RATE;
	}
}
