package com.example.week2.part2.done;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static com.example.week2.part2.done.assertion.DiscountAssert.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class AcceptanceTests {

	@Nested
	class UnitTests {
		@Test
		void should_throw_exception_when_not_enough_arguments_were_passed() {
			thenThrownBy(() -> Week2Part2.main(new String[] {"foo", "bar"}))
					.isInstanceOf(IllegalArgumentException.class)
					.hasMessageContaining("Wrong number of arguments");
		}
	}

	@Nested
	@SpringJUnitConfig(classes = DiscountConfiguration.class)
	@TestPropertySource(properties = { "name.threshold=3", "name.discount=10.0", "goods.threshold=5", "goods.discount=5.0"})
	class IntegrationTests {

		@Test
		void should_calculate_maximum_discount(@Autowired DiscountCalculator discountCalculator, @Value("${name.discount}") double nameDiscount, @Value("${goods.discount}") double goodsDiscount) {
			int goodsAboveThreshold = 100;
			Occupation maxDiscountOccupation = Occupation.EMPLOYED;
			Person person = new Person("name with length above threshold", goodsAboveThreshold, maxDiscountOccupation);

			Discount discount = discountCalculator.calculateTotalDiscountRate(person);

			then(discount)
					.as("Maximum discount is <" + nameDiscount + "> for name, <" + goodsDiscount + "> for no of goods, <10> for occupation")
					.isEqualTo(nameDiscount + goodsDiscount + 10);
		}

	}

}
