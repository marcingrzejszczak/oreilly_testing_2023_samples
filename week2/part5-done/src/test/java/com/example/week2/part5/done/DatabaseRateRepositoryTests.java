package com.example.week2.part5.done;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DatabaseRateRepositoryTests {

	@Nested
	class IntegrationTests implements DbDiscountTesting {

		DatabaseRateRepository repository;

		@BeforeEach
		void setup() {
			repository = new DatabaseRateRepository(DB_USER, DB_PASSWORD, jdbcUrl());
		}

		@Test
		void should_store_data_in_a_database() throws SQLException {
			String discountName = "for employed";
			repository.save(new Discount(discountName, Occupation.EMPLOYED, 10));

			thenSingleDiscountWasStoredInDb(discountName, Occupation.EMPLOYED, 10);
		}

		@Test
		void should_get_discount_rate_from_db() throws SQLException {
			givenDiscountRateWasInDatabase("for unemployed", Occupation.UNEMPLOYED, 5);

			then(repository.getDiscountRate(Occupation.UNEMPLOYED)).isEqualTo(5);
		}

	}

	@Nested
	class UnitTests {

		@ParameterizedTest(name = "{index} {0}")
		@MethodSource("repoActions")
		void should_throw_exception_when_issues_with_connection(String name, Consumer<DatabaseRateRepository> consumer) throws Exception {
			Connection connection = mock();
			given(connection.createStatement()).willThrow(new SQLException("BOOM!"));
			DatabaseRateRepository repository = new DatabaseRateRepository("foo", "bar", "baz") {
				@Override
				Connection getConnection() {
					return connection;
				}
			};

			thenThrownBy(() -> consumer.accept(repository))
					.hasRootCauseInstanceOf(SQLException.class)
					.hasMessageContaining("Failed to communicate with the database")
					.hasRootCauseMessage("BOOM!");
		}

		private static Stream<Arguments> repoActions() {
			return Stream.of(
					Arguments.of("for <save> method", (Consumer<DatabaseRateRepository>) repo -> repo.save(new Discount("name", Occupation.EMPLOYED, 15))),
					Arguments.of("for <getDiscountRate> method", (Consumer<DatabaseRateRepository>) repo -> repo.getDiscountRate(Occupation.EMPLOYED))
			);
		}
	}
}
