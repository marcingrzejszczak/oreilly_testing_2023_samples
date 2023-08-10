package com.example.week3.part3;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import static com.example.week3.part3.assertion.DiscountAssert.then;

class DiscountCalculatorTests {

	@Test
	void should_calculate_total_discount_when_appliers_present() {
		double fixedDiscountRate1 = 5D;
		double fixedDiscountRate2 = 7D;
		DiscountCalculator discountCalculator = new DiscountCalculator(Arrays.asList(person -> fixedDiscountRate1, person -> fixedDiscountRate2));

		Discount discount = discountCalculator.calculateTotalDiscountRate(person());

		then(discount).isEqualTo(fixedDiscountRate1 + fixedDiscountRate2);
	}

	private static Person person() {
		return new Person("test", 1, Occupation.UNEMPLOYED);
	}
}
