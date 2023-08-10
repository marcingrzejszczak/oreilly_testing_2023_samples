package com.example.week1.part2.done;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.BDDAssertions.then;


class NoOfBoughtGoodsDiscountApplierTests {

	@Test
	void should_return_a_discount_rate_when_person_has_no_of_goods_above_threshold() {
		NoOfBoughtGoodsDiscountApplier applier = new NoOfBoughtGoodsDiscountApplier();

		double discountRate = applier.getDiscountRate(personWithNumberOfGoodsAboveThreshold());

		then(discountRate).isEqualTo(NoOfBoughtGoodsDiscountApplier.DISCOUNT_RATE);
	}

	@Test
	void should_return_a_discount_rate_when_person_has_no_of_goods_equal_to_threshold() {
		NoOfBoughtGoodsDiscountApplier applier = new NoOfBoughtGoodsDiscountApplier();

		double discountRate = applier.getDiscountRate(personWithNumberOfGoodsEqualToThreshold());

		then(discountRate).isEqualTo(NoOfBoughtGoodsDiscountApplier.DISCOUNT_RATE);
	}

	@Test
	void should_return_no_discount_rate_when_person_has_no_of_goods_below_threshold() {
		NoOfBoughtGoodsDiscountApplier applier = new NoOfBoughtGoodsDiscountApplier();

		double discountRate = applier.getDiscountRate(personWithNumberOfGoodsBelowThreshold());

		then(discountRate).isEqualTo(0D);
	}

	// TODO: Example of a parametrized test
	/**
	 * Check {@link NoOfBoughtGoodsDiscountApplier#THRESHOLD} for threshold value
	 * @param noOfGoods number of goods that a person bought
	 * @param expectedDiscountRate expected discount rate
	 */
	@ParameterizedTest(name = "[{index}] For no of goods <{0}> expected rate is <{1}>")
	@CsvSource({"5,5", "6,5", "4,0"})
	void should_calculate_rate_based_on_number_of_goods(int noOfGoods, double expectedDiscountRate) {
		NoOfBoughtGoodsDiscountApplier applier = new NoOfBoughtGoodsDiscountApplier();

		double discountRate = applier.getDiscountRate(personWithNoOfGoods(noOfGoods));

		then(discountRate).isEqualTo(expectedDiscountRate);
	}

	private static Person personWithNumberOfGoodsEqualToThreshold() {
		return new Person("name", NoOfBoughtGoodsDiscountApplier.THRESHOLD, Occupation.UNEMPLOYED);
	}

	private static Person personWithNumberOfGoodsAboveThreshold() {
		return new Person("name", NoOfBoughtGoodsDiscountApplier.THRESHOLD + 1, Occupation.UNEMPLOYED);
	}

	private static Person personWithNumberOfGoodsBelowThreshold() {
		return new Person("name", NoOfBoughtGoodsDiscountApplier.THRESHOLD - 1, Occupation.UNEMPLOYED);
	}

	private static Person personWithNoOfGoods(int noOfGoods) {
		return new Person("name", noOfGoods, Occupation.UNEMPLOYED);
	}
}
