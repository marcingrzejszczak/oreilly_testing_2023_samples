package com.example.week1.part2.done;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.BDDAssertions.then;


class NameDiscountApplierTests {

	@Test
	void should_return_a_discount_rate_when_person_has_a_long_enough_name() {
		NameDiscountApplier applier = new NameDiscountApplier();

		double discountRate = applier.getDiscountRate(personWithNameLengthAboveThreshold());

		then(discountRate).isEqualTo(NameDiscountApplier.DISCOUNT_RATE);
	}

	@Test
	void should_return_a_discount_rate_when_person_has_no_of_goods_equal_to_threshold() {
		NameDiscountApplier applier = new NameDiscountApplier();

		double discountRate = applier.getDiscountRate(personWithNameLengthEqualToThreshold());

		then(discountRate).isEqualTo(NameDiscountApplier.DISCOUNT_RATE);
	}

	@Test
	void should_return_no_discount_rate_when_person_has_no_of_goods_below_threshold() {
		NameDiscountApplier applier = new NameDiscountApplier();

		double discountRate = applier.getDiscountRate(personWithNameLengthBelowThreshold());

		then(discountRate).isZero();
	}

	// TODO: Example of a parametrized test
	/**
	 * Check {@link NameDiscountApplier#THRESHOLD} for threshold value
	 * @param name person name
	 * @param expectedDiscountRate expected discount rate
	 */
	@ParameterizedTest(name = "[{index}] For a name <{0}> expected rate is <{1}>")
	@CsvSource({"nam,8", "name above threshold,8", "na,0"})
	void should_calculate_rate_based_on_name(String name, double expectedDiscountRate) {
		NameDiscountApplier applier = new NameDiscountApplier();

		double discountRate = applier.getDiscountRate(personWithName(name));

		then(discountRate).isEqualTo(expectedDiscountRate);
	}

	private static Person personWithNameLengthEqualToThreshold() {
		return new Person("nam", 0, Occupation.UNEMPLOYED);
	}

	private static Person personWithNameLengthAboveThreshold() {
		return new Person("long enough name", 0, Occupation.UNEMPLOYED);
	}

	private static Person personWithNameLengthBelowThreshold() {
		return new Person("na", 0, Occupation.UNEMPLOYED);
	}

	private static Person personWithName(String name) {
		return new Person(name, 0, Occupation.UNEMPLOYED);
	}
}
