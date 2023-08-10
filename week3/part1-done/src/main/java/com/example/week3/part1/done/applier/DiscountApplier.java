package com.example.week3.part1.done.applier;

import java.util.Arrays;
import java.util.List;

import com.example.week3.part1.done.model.Person;
import com.example.week3.part1.done.repository.RateRepository;

public interface DiscountApplier {
	double getDiscountRate(Person person);

	static List<DiscountApplier> defaultAppliers() {
		return Arrays.asList(new OccupationDiscountApplier(RateRepository.defaultRateRepository()), new NoOfBoughtGoodsDiscountApplier(), new NameDiscountApplier());
	}
}
