package com.example.week1.part2.done;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

class OccupationDiscountApplierTests {

	@Test
	void should_return_a_discount_rate_from_a_repository() {
		double fixedRate = 10D;
		OccupationDiscountApplier applier = new OccupationDiscountApplier(occupation -> fixedRate);

		double discountRate = applier.getDiscountRate(person());

		then(discountRate)
				.as("Discount rate should be taken directly from the repository")
				.isEqualTo(fixedRate);
	}

	private static Person person() {
		return new Person("name", 100, Occupation.UNEMPLOYED);
	}
}
