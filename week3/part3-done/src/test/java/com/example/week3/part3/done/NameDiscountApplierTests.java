package com.example.week3.part3.done;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class NameDiscountApplierTests {

	@ParameterizedTest(name = "[{index}] For name <{0}> expected discount is <{1}>")
	@CsvSource(textBlock = """
			n, 0
			nam, 8
			name, 8
			null, 0
			""", nullValues = "null")
	void should_return_discount_for_name(String name, double result) {
		BDDAssertions.then(new NameDiscountApplier().getDiscountRate(new Person(name, 0, Occupation.UNEMPLOYED))).isEqualTo(result);
	}

}
