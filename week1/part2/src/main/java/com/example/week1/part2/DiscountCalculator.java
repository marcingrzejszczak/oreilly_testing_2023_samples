package com.example.week1.part2;

import java.util.List;

public class DiscountCalculator {
	private List<DiscountApplier> discountAppliers;

	public Discount calculateTotalDiscountRate(Person person) {
		System.out.println("Calculating discount rate for person [" + person + "]");
		return new Discount(this.discountAppliers
				.stream()
				.map(discountApplier -> discountApplier.getDiscountRate(person))
				.reduce(0D, Double::sum));
	}

}
