package com.example.week2.part5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;

import static org.assertj.core.api.BDDAssertions.then;

// TODO: Make it a testcontainers test
public interface DbDiscountTesting {

	Logger log = LoggerFactory.getLogger(DbUtils.class);

	String DB_NAME = "db";

	String DB_USER = "root";

	String DB_PASSWORD = "example";

	int EXPOSED_PORT = 3306;


	// TODO: Add missing annotation
	// TODO: Add container to mysql:8.0.33 with MYSQL_DATABASE env var equal to DB_NAME, MYSQL_ROOT_PASSWORD equal to DB_PASSWORD and exposed port EXPOSED_PORT
	GenericContainer mysql = null;

	@BeforeEach
	default void setupDb() throws SQLException {
		DbUtils.createDiscountTableIfNotExists(getConnection());
	}

	default Connection getConnection() throws SQLException {
		return DbUtils.getConnection(jdbcUrl(), DB_USER, DB_PASSWORD);
	}

	default String jdbcUrl() {
		return "jdbc:mysql://localhost:" + mysql.getMappedPort(EXPOSED_PORT) + "/" + DB_NAME;
	}

	default void givenDiscountRateWasInDatabase(String discountName, Occupation occupation, double rate) throws SQLException {
		try (Connection con = getConnection();
			 PreparedStatement pstmt = con.prepareStatement("INSERT INTO discounts(name, occupation, rate) VALUES(?, ?, ?)")) {
			log.info("Inserting discount [{}, {}, {}] into discounts table", discountName, occupation, rate);
			pstmt.setString(1, discountName);
			pstmt.setString(2, occupation.toString());
			pstmt.setDouble(3, rate);
			pstmt.execute();
		}
	}

	default void thenSingleDiscountWasStoredInDb(String name, Occupation occupation, double rate) throws SQLException {
		try (Connection con = getConnection()) {
			try (Statement stmt = con.createStatement()) {
				String selectSql = """
							SELECT count(*) FROM discounts WHERE name="%s" and occupation="%s" and rate=%s;""".formatted(name, occupation, rate);
				int size;
				try (ResultSet resultSet = stmt.executeQuery(selectSql)) {
					resultSet.next();
					size = resultSet.getInt(1);
				}
				then(size).isEqualTo(1);
			}
		}
	}
}
