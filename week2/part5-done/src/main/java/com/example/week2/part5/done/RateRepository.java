package com.example.week2.part5.done;

public interface RateRepository {
	void save(Discount discount);

	double getDiscountRate(Occupation occupation);
}
