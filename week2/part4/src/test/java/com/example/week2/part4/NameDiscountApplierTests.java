package com.example.week2.part4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// TODO: Bonus
class NameDiscountApplierTests {

	@Test
	void should_emit_discount_calculated_event_when_discount_calculated() {

	}

	@ParameterizedTest(name = "[{index}] For a name <{0}> expected rate is <{1}>")
	@CsvSource({"nam,8", "name above threshold,8", "na,0"})
	void should_calculate_rate_based_on_name(String name, double expectedDiscountRate) {

	}

}
