package com.example.week2.part2.done;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.BDDAssertions.then;


class NoOfBoughtGoodsDiscountApplierTests {

	private static int THRESHOLD = 6;

	private static double DISCOUNT_RATE = 5D;

	/**
	 * @param noOfGoods number of goods that a person bought
	 * @param expectedDiscountRate expected discount rate
	 */
	@ParameterizedTest(name = "[{index}] For no of goods <{0}> expected rate is <{1}>")
	@CsvSource({"6,5", "7,5", "4,0"})
	void should_calculate_rate_based_on_number_of_goods(int noOfGoods, double expectedDiscountRate) {
		NoOfBoughtGoodsDiscountApplier applier = new NoOfBoughtGoodsDiscountApplier(THRESHOLD, DISCOUNT_RATE);

		double discountRate = applier.getDiscountRate(personWithNoOfGoods(noOfGoods));

		then(discountRate).isEqualTo(expectedDiscountRate);
	}

	private static Person personWithNoOfGoods(int noOfGoods) {
		return new Person("name", noOfGoods, Occupation.UNEMPLOYED);
	}
}
