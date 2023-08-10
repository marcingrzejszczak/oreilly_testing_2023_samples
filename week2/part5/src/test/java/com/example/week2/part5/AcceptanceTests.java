package com.example.week2.part5;

import java.sql.SQLException;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AcceptanceTests {

	@Nested
	class UnitTests {
		@Test
		void should_throw_exception_when_not_enough_arguments_were_passed() {
			// TODO: Check how <Week2Part5.main> works with not enough arguments
		}
	}

	@Nested
	class IntegrationTests implements DbDiscountTesting {

		@Test
		void should_calculate_maximum_discount() throws SQLException {
			// TODO: Implement me
			// given - a discount rate was inserted to the database
			// when - calculate discount for a person
			// then - max discount is 8 for name, 5 for goods, 10 for occupation - total 23

			// hint: check the DbDiscountTesting class for helper methods
		}

	}

}
