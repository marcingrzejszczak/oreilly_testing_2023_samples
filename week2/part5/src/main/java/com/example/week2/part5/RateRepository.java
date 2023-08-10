package com.example.week2.part5;

public interface RateRepository {
	void save(Discount discount);

	double getDiscountRate(Occupation occupation);
}
