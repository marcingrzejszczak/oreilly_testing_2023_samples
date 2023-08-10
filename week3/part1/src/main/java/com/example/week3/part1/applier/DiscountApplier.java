package com.example.week3.part1.applier;

import java.util.Arrays;
import java.util.List;

import com.example.week3.part1.model.Person;
import com.example.week3.part1.repository.RateRepository;

public interface DiscountApplier {
	double getDiscountRate(Person person);

	static List<DiscountApplier> defaultAppliers() {
		return Arrays.asList(new OccupationDiscountApplication(RateRepository.defaultRateRepository()), new NoOfBoughtGoodsDiscountApplierImpl(), new NameDiscountAp());
	}
}
