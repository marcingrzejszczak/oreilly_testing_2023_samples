package com.example.week1.part3.done;

// TODO: Write an integration test for this
public class DatabaseRateRepository implements RateRepository {
	@Override
	public double getDiscountRate(Occupation occupation) {
		return OccupationUtils.getRateForOccupation(occupation);
	}
}
