package com.example.week2.part4.assertion;

import com.example.week2.part4.Occupation;
import com.example.week2.part4.Person;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PersonAssertTests {

	@Test
	void should_not_throw_exception_when_no_discount() {
		PersonAssert personAssert = new PersonAssert(new Person("foo", 5, Occupation.UNEMPLOYED));

		assertThatNoException().isThrownBy(personAssert::doesNotHaveDiscount);
	}

	@Test
	void should_throw_exception_when_discount_present() {
		Person person = new Person("foo", 5, Occupation.UNEMPLOYED);
		person.setDiscountRate(5D);
		PersonAssert personAssert = new PersonAssert(person);

		assertThatThrownBy(personAssert::doesNotHaveDiscount)
				.isInstanceOf(AssertionError.class)
				.hasMessage("Expected person not to have any discount but the discount was <5.0>");
	}

	@Test
	void should_not_throw_exception_when_discount_rate_equal() {
		Person person = new Person("foo", 5, Occupation.UNEMPLOYED);
		person.setDiscountRate(5D);
		PersonAssert personAssert = new PersonAssert(person);

		assertThatNoException().isThrownBy(() -> personAssert.hasDiscountEqualTo(5D));
	}

	@Test
	void should_throw_exception_when_discount_rate_not_equal() {
		Person person = new Person("foo", 5, Occupation.UNEMPLOYED);
		person.setDiscountRate(2D);
		PersonAssert personAssert = new PersonAssert(person);

		assertThatThrownBy(() -> personAssert.hasDiscountEqualTo(5D))
				.isInstanceOf(AssertionError.class)
				.hasMessage("Expected person to have discount rate equal to <5.0> but the rate was <2.0>");
	}
}
