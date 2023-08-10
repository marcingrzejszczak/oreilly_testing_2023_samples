package com.example.week2.part4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// TODO: Bonus
class NoOfBoughtGoodsDiscountApplierTests {

	@Test
	void should_first_emit_discount_calculation_received_and_then_discount_calculated_event_when_discount_calculated() {

	}

	@ParameterizedTest(name = "[{index}] For no of goods <{0}> expected rate is <{1}>")
	@CsvSource({"5,5", "6,5", "4,0"})
	void should_calculate_rate_based_on_number_of_goods(int noOfGoods, double expectedDiscountRate) {

	}

}
