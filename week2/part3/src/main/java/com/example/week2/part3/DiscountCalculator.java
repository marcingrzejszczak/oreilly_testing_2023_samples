package com.example.week2.part3;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DiscountCalculator {
	private static final Logger log = LoggerFactory.getLogger(DiscountCalculator.class);

	private final List<DiscountApplier> discountAppliers;

	DiscountCalculator(List<DiscountApplier> discountAppliers) {
		this.discountAppliers = discountAppliers;
	}

	void calculateTotalDiscountRate(Person person) {
		log.info("Calculating discount rate for person [" + person + "]");
		this.discountAppliers
				.forEach(discountApplier -> discountApplier.applyDiscount(person));
	}

}
