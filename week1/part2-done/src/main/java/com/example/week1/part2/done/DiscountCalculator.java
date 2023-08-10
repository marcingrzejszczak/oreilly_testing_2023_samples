package com.example.week1.part2.done;

import java.util.List;

public class DiscountCalculator {

	private final List<DiscountApplier> discountAppliers;

	// Constructors FTW!
	public DiscountCalculator(List<DiscountApplier> discountAppliers) {
		this.discountAppliers = discountAppliers;
	}

	public Discount calculateTotalDiscountRate(Person person) {
		System.out.println("Calculating discount rate for person [" + person + "]");
		return new Discount(this.discountAppliers
				.stream()
				.map(discountApplier -> discountApplier.getDiscountRate(person))
				.reduce(0D, Double::sum));
	}

}
