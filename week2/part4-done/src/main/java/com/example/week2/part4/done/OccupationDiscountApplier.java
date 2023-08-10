package com.example.week2.part4.done;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OccupationDiscountApplier implements DiscountApplier {

	private static final Logger log = LoggerFactory.getLogger(OccupationDiscountApplier.class);

	private final RateRepository rateRepository;

	private final MessageSender messageSender;

	public OccupationDiscountApplier(RateRepository rateRepository, MessageSender messageSender) {
		this.rateRepository = rateRepository;
		this.messageSender = messageSender;
	}

	@Override
	public void applyDiscount(Person person) {
		if (person.getOccupation() == Occupation.UNEMPLOYED) {
			log.info("Not calculating any discounts from the database for unemployed");
			return;
		}
		messageSender.sendMessage(new Message(EventType.DISCOUNT_CALCULATION_RECEIVED));
		log.info("Calculating occupation discount");
		try {
			double discountRate = rateRepository.getDiscountRate(person.getOccupation());
			person.setDiscountRate(person.getDiscountRate() + discountRate);
			log.info("Occupation rate [{}]", discountRate);
		} catch (Exception ex) {
			log.info("Exception occurred while trying to access the repository", ex);
			messageSender.sendMessage(new Message(EventType.ERROR));
		}
	}
}
