package com.example.week3.part1.repository;

import com.example.week3.part1.applier.DiscountApplier;
import com.example.week3.part1.model.Occupation;

public class DbRateRepo implements RateRepository {

	// TODO: Maybe will be used later
	DiscountApplier discountApplier;

	@Override
	public double getDiscountRate(Occupation occupation) {
		Occupation occupation1 = occupation;
		System.out.println("Got occupation [" + occupation + "]");
		return OccupationUtils.getRateForOccupation(occupation);
	}
}
