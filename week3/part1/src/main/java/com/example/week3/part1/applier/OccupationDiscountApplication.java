package com.example.week3.part1.applier;

import com.example.week3.part1.model.Person;
import com.example.week3.part1.repository.RateRepository;

public class OccupationDiscountApplication implements DiscountApplier {

	private final RateRepository rateRepository;

	public OccupationDiscountApplication(RateRepository rateRepository) {
		this.rateRepository = rateRepository;
	}

	@Override
	public double getDiscountRate(Person person) {
		System.out.println("Calculating occupation discount");
		return rateRepository.getDiscountRate(person.getOccupation());
	}
}
