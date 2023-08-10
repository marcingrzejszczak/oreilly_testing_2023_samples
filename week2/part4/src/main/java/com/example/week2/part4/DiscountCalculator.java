package com.example.week2.part4;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscountCalculator {

	private static final Logger log = LoggerFactory.getLogger(DiscountCalculator.class);

	private final MessageSender messageSender;

	private final List<DiscountApplier> discountAppliers;

	public DiscountCalculator(MessageSender messageSender, List<DiscountApplier> discountAppliers) {
		this.messageSender = messageSender;
		this.discountAppliers = discountAppliers;
	}

	public void calculateTotalDiscountRate(Person person) {
		log.info("Calculating discount rate for person [{}]", person);
		this.discountAppliers
				.forEach(discountApplier -> discountApplier.applyDiscount(person));
		log.info("Total discount rate for person [{}] is equal to [{}]", person, person.getDiscountRate());
		messageSender.sendMessage(person);
	}

}
