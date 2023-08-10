package com.example.week3.part4;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscountCalculator {
	private static final Logger log = Logger.getLogger(DiscountCalculator.class.getName());

	static final String DISCOUNT_PREFIX = "total_";

	private final List<DiscountApplier> discountAppliers;

	public DiscountCalculator(List<DiscountApplier> discountAppliers) {
		this.discountAppliers = discountAppliers;
	}

	public Discount calculateTotalDiscountRate(Person person) {
		log.log(Level.INFO, () -> "Calculating discount rate for person [" + person + "]");
		if (person == null) {
			return new Discount("total", 0D);
		}
		return new Discount(name(person), this.discountAppliers
				.stream()
				.map(discountApplier -> discountApplier.getDiscountRate(person))
				.reduce(0D, Double::sum));
	}

	private String name(Person person) {
		int numberOfGoods = person.getNumberOfBoughtGoods();
		long suffix = numberOfGoods + person.getCreationTimestamp(); // TODO: Fix me - what if you buy A LOT of goods and you have a BIG creation timestamp? Maybe you should look into BigDecimals?
		return DISCOUNT_PREFIX + suffix;
	}

}
