package com.example.week2.part5.done;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DbUtils {

	private static final Logger log = LoggerFactory.getLogger(DbUtils.class);

	static void createDiscountTableIfNotExists(Connection con) throws SQLException {
		try (Statement stmt = con.createStatement()) {
			log.info("Creating discounts table if not present");
			String tableSql = """
CREATE TABLE IF NOT EXISTS discounts (discount_id int PRIMARY KEY AUTO_INCREMENT, name varchar(30), occupation varchar(30), rate double)""";
			stmt.execute(tableSql);
		}
	}

	static Connection getConnection(String url, String user, String password) throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}
}
