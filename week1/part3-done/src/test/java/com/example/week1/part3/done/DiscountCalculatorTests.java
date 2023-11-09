package com.example.week1.part3.done;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import static com.example.week1.part3.done.assertion.PersonAssert.then;

class DiscountCalculatorTests {
	@Test
	void should_calculate_no_discount_when_no_appliers_present() {
		DiscountCalculator discountCalculator = new DiscountCalculator(Collections.emptyList());
		Person person = person();

		discountCalculator.calculateTotalDiscountRate(person);

		then(person).doesNotHaveDiscount();
	}

	@Test
	void should_calculate_total_discount_when_appliers_present() {
		double fixedDiscountRate1 = 5D;
		double fixedDiscountRate2 = 7D;
		DiscountCalculator discountCalculator = new DiscountCalculator(Arrays.asList(person -> person.setDiscountRate(person.getDiscountRate() + fixedDiscountRate1), person -> person.setDiscountRate(person.getDiscountRate() + fixedDiscountRate2)));
		Person person = person();

		discountCalculator.calculateTotalDiscountRate(person);

		then(person).hasDiscountEqualTo(fixedDiscountRate1 + fixedDiscountRate2);
	}

	private static Person person() {
		return new Person("test", 1, Occupation.UNEMPLOYED);
	}
}
