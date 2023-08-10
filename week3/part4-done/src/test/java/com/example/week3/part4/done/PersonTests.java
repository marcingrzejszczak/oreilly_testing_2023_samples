package com.example.week3.part4.done;

import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.Negative;
import net.jqwik.api.constraints.Positive;
import net.jqwik.api.constraints.StringLength;
import net.jqwik.api.constraints.WithNull;

import static org.assertj.core.api.BDDAssertions.thenNoException;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class PersonTests {

	@Property
	void should_create_person(@ForAll @Positive int creationTimestamp, @ForAll @StringLength(min = 2) String name, @ForAll @Positive int goods, @ForAll Occupation occupation) {
		thenNoException().isThrownBy(() -> new Person(creationTimestamp, name, goods, occupation));
	}

	@Property(tries = 10)
	void should_fail_to_create_person_because_of_invalid_name(@ForAll @StringLength(max = 1) @WithNull String name) {
		thenThrownBy(() -> new Person(1, name, 1, Occupation.UNEMPLOYED))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Name can't be empty");
	}

	@Property(tries = 10)
	void should_fail_to_create_person_because_of_invalid_number_of_goods(@ForAll @Negative int numberOfGoods) {
		thenThrownBy(() -> new Person(1, "some name", numberOfGoods, Occupation.UNEMPLOYED))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Number of goods can't be negative");
	}

	@Property(tries = 10)
	void should_fail_to_create_person_because_of_invalid_timestamp(@ForAll @Negative int timestamp) {
		thenThrownBy(() -> new Person(timestamp, "some name", 10, Occupation.UNEMPLOYED))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Timestamp can't be negative");
	}

	@Example
	void should_fail_to_create_person_because_of_invalid_occupation() {
		thenThrownBy(() -> new Person(1, "some name", 10, null))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Object can't be empty");
	}
}
