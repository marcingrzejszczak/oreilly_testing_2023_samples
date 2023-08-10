package com.example.week2.part5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseRateRepository implements RateRepository {

	private static final Logger log = LoggerFactory.getLogger(DatabaseRateRepository.class);

	private static final String insertDiscountStmnt = "INSERT INTO discounts(name, occupation, rate) VALUES(?, ?, ?)";

	private static final String getDiscountStmnt = "SELECT rate FROM discounts WHERE occupation=\"%s\"";

	private final String user;

	private final String password;

	private final String databaseUrl;

	public DatabaseRateRepository(String user, String password, String databaseUrl) {
		this.user = user;
		this.password = password;
		this.databaseUrl = databaseUrl;
	}

	@Override
	public void save(Discount discount) {
		try (Connection con = getConnection()) {
			DbUtils.createDiscountTableIfNotExists(con);
			try (PreparedStatement pstmt = con.prepareStatement(insertDiscountStmnt)) {
				pstmt.setString(1, discount.getName());
				pstmt.setString(2, discount.getOccupation().toString());
				pstmt.setDouble(3, discount.getRate());
				pstmt.execute();
				log.info("Stored following values [name={}, occupation={}, rate={}] in the database",
						discount.getName(), discount.getOccupation(), discount.getRate());
			}
		}
		catch (SQLException e) {
			throw new RuntimeException("Failed to communicate with the database", e);
		}
	}

	@Override
	public double getDiscountRate(Occupation occupation) {
		try (Connection con = getConnection()) {
			DbUtils.createDiscountTableIfNotExists(con);
			try (Statement stmt = con.createStatement()) {
				String selectSql = getDiscountStmnt.formatted(occupation.toString());
				try (ResultSet resultSet = stmt.executeQuery(selectSql)) {
					resultSet.next();
					double rate = resultSet.getDouble(1);
					log.info("Found the following rate [{}] for occupation [{}]", rate, occupation);
					return rate;
				}
			}
		}
		catch (SQLException e) {
			throw new RuntimeException("Failed to communicate with the database", e);
		}
	}

	Connection getConnection() throws SQLException {
		return DbUtils.getConnection(databaseUrl, user, password);
	}

}
