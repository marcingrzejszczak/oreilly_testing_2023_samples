package com.example.week2.part2.done;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.BDDAssertions.then;


class NameDiscountApplierTests {

	private static final int THRESHOLD = 4;

	private static final double DISCOUNT_RATE = 8D;

	/**
	 * @param name person name
	 * @param expectedDiscountRate expected discount rate
	 */
	@ParameterizedTest(name = "[{index}] For a name <{0}> expected rate is <{1}>")
	@CsvSource({"name,8", "name above threshold,8", "na,0"})
	void should_calculate_rate_based_on_name(String name, double expectedDiscountRate) {
		NameDiscountApplier applier = new NameDiscountApplier(THRESHOLD, DISCOUNT_RATE);

		double discountRate = applier.getDiscountRate(personWithName(name));

		then(discountRate).isEqualTo(expectedDiscountRate);
	}

	private static Person personWithName(String name) {
		return new Person(name, 0, Occupation.UNEMPLOYED);
	}
}
