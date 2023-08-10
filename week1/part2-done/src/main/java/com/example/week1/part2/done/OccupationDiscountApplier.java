package com.example.week1.part2.done;

public class OccupationDiscountApplier implements DiscountApplier {

	private final RateRepository rateRepository;

	public OccupationDiscountApplier(RateRepository rateRepository) {
		this.rateRepository = rateRepository;
	}

	@Override
	public double getDiscountRate(Person person) {
		System.out.println("Calculating occupation discount");
		return rateRepository.getDiscountRate(person.getOccupation());
	}
}
