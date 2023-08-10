package com.example.week3.part1.applier;

import com.example.week3.part1.model.Person;

public class NoOfBoughtGoodsDiscountApplierImpl implements DiscountApplier {

	// This should be configurable
	static int THRESHOLD = 5;

	static double DISCOUNT_RATE = 5D;

	public double getDiscountRate(Person person) {
		System.out.println("Calculating number of goods discount");
		if (person.getNumberOfBoughtGoods() < THRESHOLD) {
			return 0D;
		}
		return DISCOUNT_RATE;
	}
}
