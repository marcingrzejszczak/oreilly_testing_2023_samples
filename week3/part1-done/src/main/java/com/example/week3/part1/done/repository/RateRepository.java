package com.example.week3.part1.done.repository;

import com.example.week3.part1.done.model.Occupation;

public interface RateRepository {
	double getDiscountRate(Occupation occupation);

	static RateRepository defaultRateRepository() {
		return new DbRateRepository();
	}
}
