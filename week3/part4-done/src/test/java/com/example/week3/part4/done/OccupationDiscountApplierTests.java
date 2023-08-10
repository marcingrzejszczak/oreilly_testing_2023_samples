package com.example.week3.part4.done;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;

import static org.assertj.core.api.BDDAssertions.then;

class OccupationDiscountApplierTests {

	@Property
	void should_return_discount_for_any_occupation(@ForAll Occupation occupation) {
		then(new OccupationDiscountApplier().getDiscountRate(new Person("any name", 10, occupation))).isEqualTo(OccupationDiscountApplier.DISCOUNT_RATE);
	}
}
