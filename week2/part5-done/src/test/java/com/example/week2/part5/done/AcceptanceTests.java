package com.example.week2.part5.done;

import java.sql.SQLException;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.example.week2.part5.done.assertion.DiscountAssert.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;

class AcceptanceTests {

	@Nested
	class UnitTests {
		@Test
		void should_throw_exception_when_not_enough_arguments_were_passed() {
			thenThrownBy(() -> Week2Part5.main(new String[] {"foo", "bar", "baz"}))
					.isInstanceOf(IllegalArgumentException.class)
					.hasMessageContaining("Wrong number of arguments");
		}
	}

	@Nested
	class IntegrationTests implements DbDiscountTesting {

		@Test
		void should_calculate_maximum_discount() throws SQLException {
			givenDiscountRateWasInDatabase("for employed", Occupation.EMPLOYED, 10);

			Discount calculatedDiscount = new Week2Part5(DB_USER, DB_PASSWORD, jdbcUrl()).calculateDiscount(new Person("foo", 100, Occupation.EMPLOYED));

			then(calculatedDiscount)
					.as("Maximum discount is <8> for name, <5> for no of goods, <10> for occupation; total of <23>")
					.isEqualTo(23);
		}

	}

}
