package com.example.week2.part5.done;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscountCalculator {

	private static final Logger log = LoggerFactory.getLogger(DiscountCalculator.class);

	private final List<DiscountApplier> discountAppliers;

	public DiscountCalculator(List<DiscountApplier> discountAppliers) {
		this.discountAppliers = discountAppliers;
	}

	public Discount calculateTotalDiscountRate(Person person) {
		log.info("Calculating discount rate for person [{}]", person);
		return new Discount(person.getName(), person.getOccupation(), this.discountAppliers
				.stream()
				.map(discountApplier -> discountApplier.getDiscountRate(person))
				.reduce(0D, Double::sum));
	}

}
