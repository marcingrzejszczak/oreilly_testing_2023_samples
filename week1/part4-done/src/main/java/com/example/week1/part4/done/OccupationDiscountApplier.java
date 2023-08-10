package com.example.week1.part4.done;

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
	public Discount getDiscountRate(Person person) {
		if (person.getOccupation() == Occupation.UNEMPLOYED) {
			log.info("Not calculating any discounts from the database for unemployed");
			return new Discount(getName(), 0);
		}
		messageSender.sendMessage(new Message(EventType.DISCOUNT_CALCULATION_RECEIVED));
		log.info("Calculating occupation discount");
		Latency.simulateRandomLatency();
		try {
			double rate = rateRepository.getDiscountRate(person.getOccupation());
			if (rate < 0) {
				throw new NegativeRateException(rate);
			}
			return new Discount(getName(), rate);
		}
		catch (Exception ex) {
			log.error("Exception occurred while trying to access the repository", ex);
			messageSender.sendMessage(new Message(EventType.ERROR));
			return new Discount(getName(), 0);
		}
	}

	@Override
	public String getName() {
		return "occupation";
	}
}
