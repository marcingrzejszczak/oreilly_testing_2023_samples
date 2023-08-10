package com.example.week3.part3;

import java.util.logging.Level;
import java.util.logging.Logger;

public class OccupationDiscountApplier implements DiscountApplier {
	private static final Logger log = Logger.getLogger(OccupationDiscountApplier.class.getName());

	private final RateRepository rateRepository;

	public OccupationDiscountApplier(RateRepository rateRepository) {
		this.rateRepository = rateRepository;
	}

	@Override
	public double getDiscountRate(Person person) {
		log.log(Level.INFO, "Calculating occupation discount");
		if (person.getOccupation() != null) {
			return rateRepository.getDiscountRate(person.getOccupation());
		}
		return 0D;
	}
}
