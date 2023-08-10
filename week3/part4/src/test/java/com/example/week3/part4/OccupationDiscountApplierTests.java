package com.example.week3.part4;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.BDDAssertions.then;

class OccupationDiscountApplierTests {

	// TODO: Fix me - convert to 1 Property Based Test - check the docs on how to set combinations of enums
	@ParameterizedTest(name = "[{index}] For occupation <{0}> expected discount is <{1}>")
	@CsvSource(textBlock = """
			EMPLOYED, 10
			UNEMPLOYED, 10
			""")
	void should_return_discount_for_occupation(Occupation occupation, double result) {
		then(new OccupationDiscountApplier().getDiscountRate(new Person("foo", 1, occupation))).isEqualTo(result);
	}

}
