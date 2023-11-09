package com.example.week1.part2.done;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.example.week1.part2.done.assertion.DiscountAssert.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

@Tag("slow") // Connects to a database - we will work on fixing that in the next lessons
class AcceptanceTests {
	@Test
	void should_throw_exception_when_not_enough_arguments_were_passed() {
		thenThrownBy(() -> Week1Part2.main(new String[] {"foo", "bar"}))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("Wrong number of arguments");
	}

	@Test
	void should_calculate_maximum_discount() {
		int goodsAboveThreshold = 100;
		Occupation maxDiscountOccupation = Occupation.EMPLOYED;
		Person person = new Person("name with length above threshold", goodsAboveThreshold, maxDiscountOccupation);

		Discount discount = new Week1Part2().calculateDiscount(person);

		then(discount)
				.as("Maximum discount is <8> for name, <5> for no of goods, <10> for occupation; total of <23>")
				.isEqualTo(23);
	}

}
