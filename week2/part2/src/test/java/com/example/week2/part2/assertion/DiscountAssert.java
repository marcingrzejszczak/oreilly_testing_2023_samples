package com.example.week2.part2.assertion;

import java.util.Objects;

import com.example.week2.part2.Discount;
import org.assertj.core.api.AbstractAssert;

public class DiscountAssert extends AbstractAssert<DiscountAssert, Discount> {

	public DiscountAssert(Discount actual) {
		super(actual, DiscountAssert.class);
	}

	public static DiscountAssert assertThat(Discount actual) {
		return new DiscountAssert(actual);
	}

	public static DiscountAssert then(Discount actual) {
		return new DiscountAssert(actual);
	}

	public DiscountAssert isNotSet() {
		isNotNull();
		if (!Objects.equals(actual.getRate(), 0D)) {
			failWithMessage("Expected not to have any discount but the discount was <%s>", actual.getRate());
		}
		return this;
	}

	public DiscountAssert isEqualTo(double rate) {
		isNotNull();
		if (!Objects.equals(actual.getRate(), rate)) {
			failWithMessage("Expected discount rate to be equal to <%s> but the rate was <%s>", rate, actual.getRate());
		}
		return this;
	}
}
