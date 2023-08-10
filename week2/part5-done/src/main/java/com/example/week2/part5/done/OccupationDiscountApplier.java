package com.example.week2.part5.done;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OccupationDiscountApplier implements DiscountApplier {

	private static final Logger log = LoggerFactory.getLogger(OccupationDiscountApplier.class);

	private final RateRepository rateRepository;

	public OccupationDiscountApplier(RateRepository rateRepository) {
		this.rateRepository = rateRepository;
	}

	@Override
	public double getDiscountRate(Person person) {
		log.info("Calculating occupation discount");
		return rateRepository.getDiscountRate(person.getOccupation());
	}
}
