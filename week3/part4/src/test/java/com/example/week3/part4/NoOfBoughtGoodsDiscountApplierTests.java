package com.example.week3.part4;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.BDDAssertions.then;


class NoOfBoughtGoodsDiscountApplierTests {

	// TODO: Fix me - convert to 2 Property Based Tests (one for discount, one for no discount)
	//  check @IntRange on how to set min / max values
	@ParameterizedTest(name = "[{index}] For number of goods <{0}> expected discount is <{1}>")
	@CsvSource(textBlock = """
			3, 0
			5, 0
			6, 5
			""")
	void should_return_discount_for_name(Integer numberOfGoods, double result) {
		then(new NoOfBoughtGoodsDiscountApplier().getDiscountRate(new Person("foo", numberOfGoods, Occupation.UNEMPLOYED))).isEqualTo(result);
	}

}
