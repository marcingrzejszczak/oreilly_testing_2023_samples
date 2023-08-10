package com.example.week3.part3;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;


class NameDiscountApplierTests {

	@Test
	void should_return_no_discount_rate_when_person_has_no_of_goods_below_threshold() {
		NameDiscountApplier applier = new NameDiscountApplier();

		double discountRate = applier.getDiscountRate(personWithNameLengthBelowThreshold());

		then(discountRate).isZero();
	}

	private static Person personWithNameLengthBelowThreshold() {
		return new Person("na", 0, Occupation.UNEMPLOYED);
	}

}
