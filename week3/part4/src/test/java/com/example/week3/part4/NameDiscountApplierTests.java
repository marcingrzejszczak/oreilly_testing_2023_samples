package com.example.week3.part4;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.BDDAssertions.then;


class NameDiscountApplierTests {

	// TODO: Fix me - convert to 2 Property Based Tests (one for discount, one for no discount)
	//  check @StringLength on how to set min / max values
	@ParameterizedTest(name = "[{index}] For name <{0}> expected discount is <{1}>")
	@CsvSource(textBlock = """
			na, 0
			nam, 0
			name, 8
			nameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee, 0
			nameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee, 0
			""")
	void should_return_discount_for_name(String name, double result) {
		then(new NameDiscountApplier().getDiscountRate(new Person(name, 0, Occupation.UNEMPLOYED))).isEqualTo(result);
	}

}
