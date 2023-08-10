package com.example.week3.part4.done;

import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Combinators;
import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;
import org.assertj.core.api.Assertions;

import static com.example.week3.part4.done.assertion.DiscountAssert.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class Week3Part4Tests {
	@Example
	void should_throw_exception_when_not_enough_arguments_were_passed() {
		thenThrownBy(() -> Week3Part4.main(new String[] {"foo", "bar"}))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("Wrong number of arguments");
	}

	@Property
	void should_calculate_maximum_discount(@ForAll("peopleWithMaximumDiscount") Person person) {
		then(new Week3Part4().calculateDiscount(person))
				.as("Maximum discount is <8> for name, <5> for no of goods, <10> for occupation; total of <23>")
				.isEqualTo(23);
	}

	@Provide
	Arbitrary<Person> peopleWithMaximumDiscount() {
		Arbitrary<Long> timestamps = Arbitraries.longs().greaterOrEqual(1);
		Arbitrary<String> names = Arbitraries.strings().alpha()
				.ofMinLength(NameDiscountApplier.LOWER_THRESHOLD + 1).ofMaxLength(NameDiscountApplier.UPPER_THRESHOLD - 1);
		Arbitrary<Integer> noOfGoods = Arbitraries.integers().greaterOrEqual(NoOfBoughtGoodsDiscountApplier.THRESHOLD + 1);
		Arbitrary<Occupation> occupations = Arbitraries.of(Occupation.class);
		return Combinators.combine(timestamps, names, noOfGoods, occupations).as(Person::new);
	}
}
