package com.example.week3.part1.repository;

import com.example.week3.part1.model.Occupation;

public interface RateRepository {
	double getDiscountRate(Occupation occupation);

	static RateRepository defaultRateRepository() {
		return new DbRateRepo();
	}
}
