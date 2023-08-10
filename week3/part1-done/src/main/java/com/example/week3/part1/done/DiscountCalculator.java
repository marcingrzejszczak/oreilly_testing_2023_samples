package com.example.week3.part1.done;

import java.util.List;

import com.example.week3.part1.done.applier.DiscountApplier;
import com.example.week3.part1.done.model.Discount;
import com.example.week3.part1.done.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscountCalculator {

	private static final Logger log = LoggerFactory.getLogger(DiscountCalculator.class);

	private final List<DiscountApplier> discountAppliers;

	// Constructors FTW!
	public DiscountCalculator(List<DiscountApplier> discountAppliers) {
		this.discountAppliers = discountAppliers;
	}

	public Discount calculateTotalDiscountRate(Person person) {
		log.info("Calculating discount rate for person [{}]", person);
		return new Discount(this.discountAppliers
				.stream()
				.map(discountApplier -> discountApplier.getDiscountRate(person))
				.reduce(0D, Double::sum));
	}

}
