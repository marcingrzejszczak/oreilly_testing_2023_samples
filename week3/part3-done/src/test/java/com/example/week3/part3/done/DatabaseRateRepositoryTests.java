package com.example.week3.part3.done;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DatabaseRateRepositoryTests {

	@ParameterizedTest(name = "[{index}] For occupation <{0}> expected discount is <{1}>")
	@CsvSource(textBlock = """
			UNEMPLOYED, 0
			EMPLOYED, 10
			null, 0
			""", nullValues = "null")
	void should_return_discount_for_occupation(Occupation occupation, double result) {
		BDDAssertions.then(new DatabaseRateRepository().getDiscountRate(occupation)).isEqualTo(result);
	}
}
