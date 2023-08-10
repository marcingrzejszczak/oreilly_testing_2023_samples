package com.example.week1.part2;

public class OccupationDiscountApplier implements DiscountApplier {

	@Override
	public double getDiscountRate(Person person) {
		System.out.println("Calculating occupation discount");
		return OccupationUtils.getRateForOccupation(person.getOccupation());
	}
}
