package com.example.week2.part5;

import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DatabaseRateRepositoryTests {

	@Nested
	class IntegrationTests implements DbDiscountTesting {

		DatabaseRateRepository repository;

		@BeforeEach
		void setup() {
			// TODO: Initialize repository - check DbDiscountTesting for username, password and jdbc urls
		}

		@Test
		void should_store_data_in_a_database() throws SQLException {
			// TODO: Fix me - write the missing test
			// when - repository stores a discount
			// then - a single discount was stored in the database

			// hint: check the DbDiscountTesting class for helper methods
		}

		@Test
		void should_get_discount_rate_from_db() throws SQLException {
			// TODO: Fix me - write the missing test
			// given - a discount rate of X for given occupation Y was inserted to the database
			// when - repository retrieves the rate
			// then - rate for given occupation Y is equal to X

			// hint: check the DbDiscountTesting class for helper methods
		}

	}

	@Nested
	class UnitTests {

		@ParameterizedTest(name = "{index} {0}")
		@MethodSource("repoActions")
		void should_throw_exception_when_issues_with_connection(String name, Consumer<DatabaseRateRepository> consumer) throws Exception {

			// TODO: Fix me - write the missing test
			// given - a mock Connection throws a SQLException on statement creation
			// given - DatabaseRateRepository returns a mock connection
			// when - repository calls a method
			// then - a SQLException was thrown

			// hint: this is a parameterized test using MethodSources. Concrete actions to be taken by the repository are provided there
		}

		private static Stream<Arguments> repoActions() {
			return Stream.of(
					Arguments.of("for <save> method", (Consumer<DatabaseRateRepository>) repo -> repo.save(new Discount("name", Occupation.EMPLOYED, 15))),
					Arguments.of("for <getDiscountRate> method", (Consumer<DatabaseRateRepository>) repo -> repo.getDiscountRate(Occupation.EMPLOYED))
			);
		}
	}
}
