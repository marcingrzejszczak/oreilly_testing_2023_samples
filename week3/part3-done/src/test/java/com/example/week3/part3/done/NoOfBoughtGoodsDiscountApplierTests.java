package com.example.week3.part3.done;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


class NoOfBoughtGoodsDiscountApplierTests {

	@ParameterizedTest(name = "[{index}] For number of goods <{0}> expected discount is <{1}>")
	@CsvSource(textBlock = """
			3, 0
			5, 5
			6, 5
			null, 0
			""", nullValues = "null")
	void should_return_discount_for_name(Integer numberOfGoods, double result) {
		BDDAssertions.then(new NoOfBoughtGoodsDiscountApplier().getDiscountRate(new Person("foo", numberOfGoods, Occupation.UNEMPLOYED))).isEqualTo(result);
	}

}
