package com.example.week3.part4;

import java.util.Arrays;
import java.util.Collections;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;

import static com.example.week3.part4.assertion.DiscountAssert.then;

class DiscountCalculatorTests {

	// TODO: Fix me - this is a good idea of an Example
	@Test
	void should_return_no_discount_when_person_null() {
		DiscountCalculator discountCalculator = new DiscountCalculator(Collections.singletonList(person -> {
			throw new AssertionError("Shouldn't be called");
		}));

		Discount discount = discountCalculator.calculateTotalDiscountRate(null);

		then(discount).isNotSet();
	}

	// TODO: Fix me - convert to Property Based Test, use @Provide to generate a valid Person
	// Hint - if your tests fail double check if your production code is OK
	@Test
	void should_calculate_total_discount_when_appliers_present() {
		double fixedDiscountRate1 = 5D;
		double fixedDiscountRate2 = 7D;
		DiscountCalculator discountCalculator = new DiscountCalculator(Arrays.asList(person -> fixedDiscountRate1, person -> fixedDiscountRate2));

		Discount discount = discountCalculator.calculateTotalDiscountRate(person());

		then(discount).isEqualTo(fixedDiscountRate1 + fixedDiscountRate2);
		String discountName = discount.getDiscountName();
		BDDAssertions.then(discountName).startsWith(DiscountCalculator.DISCOUNT_PREFIX);
		long number = Long.parseLong(discountName.substring(DiscountCalculator.DISCOUNT_PREFIX.length())); // TODO: Fix me - Are you sure this will ALWAYS work?
		BDDAssertions.then(number).isPositive();
	}

	private static Person person() {
		return new Person("test", 1, Occupation.UNEMPLOYED);
	}
}
