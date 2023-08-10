package com.example.week1.part2;

public class NoOfBoughtGoodsDiscountApplier implements DiscountApplier {

	@Override
	public double getDiscountRate(Person person) {
		System.out.println("Calculating number of goods discount");
		if (person.getNumberOfBoughtGoods() < 5) {
			return 0D;
		}
		return 5D;
	}
}
