package com.example.week3.part4.done;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Combinators;
import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;

import static com.example.week3.part4.done.assertion.DiscountAssert.then;
import static org.assertj.core.api.BDDAssertions.then;

class DiscountCalculatorTests {

	@Example
	void should_return_no_discount_when_person_null() {
		DiscountCalculator discountCalculator = new DiscountCalculator(Collections.singletonList(person -> {
			throw new AssertionError("Shouldn't be called");
		}));

		Discount discount = discountCalculator.calculateTotalDiscountRate(null);

		then(discount).isNotSet();
	}

	@Property
	void should_calculate_total_discount_when_appliers_present(@ForAll("validPeople") Person person) {
		double fixedDiscountRate1 = 5D;
		double fixedDiscountRate2 = 7D;
		DiscountCalculator discountCalculator = new DiscountCalculator(Arrays.asList(p -> fixedDiscountRate1, p -> fixedDiscountRate2));

		Discount discount = discountCalculator.calculateTotalDiscountRate(person);

		then(discount).isEqualTo(fixedDiscountRate1 + fixedDiscountRate2);
		String discountName = discount.getDiscountName();
		then(discountName).startsWith(DiscountCalculator.DISCOUNT_PREFIX);
		BigDecimal number = new BigDecimal(discountName.substring(DiscountCalculator.DISCOUNT_PREFIX.length()));
		then(number).isPositive();
	}

	@Provide
	Arbitrary<Person> validPeople() {
		Arbitrary<Long> timestamps = Arbitraries.longs().greaterOrEqual(1);
		Arbitrary<String> names = Arbitraries.strings().ofMinLength(2);
		Arbitrary<Integer> noOfGoods = Arbitraries.integers().greaterOrEqual(0);
		Arbitrary<Occupation> occupations = Arbitraries.of(Occupation.class);
		return Combinators.combine(timestamps, names, noOfGoods, occupations).as(Person::new);
	}

}
