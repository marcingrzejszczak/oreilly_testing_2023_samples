package com.example.week3.part3.done;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DiscountCalculator {
	private static final Logger log = Logger.getLogger(DiscountCalculator.class.getName());

	private final List<DiscountApplier> discountAppliers;

	public DiscountCalculator(List<DiscountApplier> discountAppliers) {
		this.discountAppliers = discountAppliers;
	}

	public Discount calculateTotalDiscountRate(Person person) {
		log.log(Level.INFO, () -> "Calculating discount rate for person [" + person + "]");
		if (person == null) {
			return new Discount(0D);
		}
		return new Discount(this.discountAppliers
				.stream()
				.map(discountApplier -> discountApplier.getDiscountRate(person))
				.reduce(0D, Double::sum));
	}

}
