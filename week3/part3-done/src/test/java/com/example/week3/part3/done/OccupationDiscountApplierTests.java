package com.example.week3.part3.done;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OccupationDiscountApplierTests {

	@ParameterizedTest(name = "[{index}] For occupation <{0}> expected discount is <{1}>")
	@CsvSource(textBlock = """
			EMPLOYED, 10
			UNEMPLOYED, 10
			null, 0
			""", nullValues = "null")
	void should_return_discount_for_occupation(Occupation occupation, double result) {
		double fixedRate = 10D;
		BDDAssertions.then(new OccupationDiscountApplier(o -> fixedRate).getDiscountRate(new Person("foo", 1, occupation))).isEqualTo(result);
	}

}
