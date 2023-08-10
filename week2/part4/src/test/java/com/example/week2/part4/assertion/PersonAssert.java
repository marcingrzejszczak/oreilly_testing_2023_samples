package com.example.week2.part4.assertion;

import java.util.Objects;

import com.example.week2.part4.Person;
import org.assertj.core.api.AbstractAssert;

public class PersonAssert extends AbstractAssert<PersonAssert, Person> {

	public PersonAssert(Person actual) {
		super(actual, PersonAssert.class);
	}

	public static PersonAssert assertThat(Person actual) {
		return new PersonAssert(actual);
	}

	public static PersonAssert then(Person actual) {
		return new PersonAssert(actual);
	}

	public PersonAssert doesNotHaveDiscount() {
		isNotNull();
		if (!Objects.equals(actual.getDiscountRate(), 0D)) {
			failWithMessage("Expected person not to have any discount but the discount was <%s>", actual.getDiscountRate());
		}
		return this;
	}

	public PersonAssert hasDiscountEqualTo(double rate) {
		isNotNull();
		if (!Objects.equals(actual.getDiscountRate(), rate)) {
			failWithMessage("Expected person to have discount rate equal to <%s> but the rate was <%s>", rate, actual.getDiscountRate());
		}
		return this;
	}
}
