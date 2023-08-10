package com.example.week3.part3;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;


class NoOfBoughtGoodsDiscountApplierTests {

	@Test
	void should_return_a_discount_rate_when_person_has_no_of_goods_above_threshold() {
		NoOfBoughtGoodsDiscountApplier applier = new NoOfBoughtGoodsDiscountApplier();

		double discountRate = applier.getDiscountRate(personWithNumberOfGoodsAboveThreshold());

		then(discountRate).isEqualTo(NoOfBoughtGoodsDiscountApplier.DISCOUNT_RATE);
	}

	@Test
	void should_return_no_discount_rate_when_person_has_no_of_goods_below_threshold() {
		NoOfBoughtGoodsDiscountApplier applier = new NoOfBoughtGoodsDiscountApplier();

		double discountRate = applier.getDiscountRate(personWithNumberOfGoodsBelowThreshold());

		then(discountRate).isEqualTo(0D);
	}

	private static Person personWithNumberOfGoodsAboveThreshold() {
		return new Person("name", NoOfBoughtGoodsDiscountApplier.THRESHOLD + 1, Occupation.UNEMPLOYED);
	}

	private static Person personWithNumberOfGoodsBelowThreshold() {
		return new Person("name", NoOfBoughtGoodsDiscountApplier.THRESHOLD - 1, Occupation.UNEMPLOYED);
	}

}
