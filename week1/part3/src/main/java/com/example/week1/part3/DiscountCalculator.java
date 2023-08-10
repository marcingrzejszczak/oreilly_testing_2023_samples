package com.example.week1.part3;

import java.util.List;

public class DiscountCalculator {
	private final List<DiscountApplier> discountAppliers;

	public DiscountCalculator(List<DiscountApplier> discountAppliers) {
		this.discountAppliers = discountAppliers;
	}

	public void calculateTotalDiscountRate(Person person) {
		System.out.println("Calculating discount rate for person [" + person + "]");
		this.discountAppliers
				.forEach(discountApplier -> discountApplier.applyDiscount(person));
	}

}
