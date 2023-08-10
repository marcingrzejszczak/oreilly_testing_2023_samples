package com.example.week3.part1.done.applier;

import com.example.week3.part1.done.model.Person;
import com.example.week3.part1.done.repository.RateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class OccupationDiscountApplier implements DiscountApplier {

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
