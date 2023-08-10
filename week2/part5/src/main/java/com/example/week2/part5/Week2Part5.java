package com.example.week2.part5;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Week2Part5 {

	private static final Logger log = LoggerFactory.getLogger(Week2Part5.class);

	private final String user;

	private final String password;

	private final String databaseUrl;

	public Week2Part5(String user, String password, String databaseUrl) {
		this.user = user;
		this.password = password;
		this.databaseUrl = databaseUrl;
	}

	// Arguments
	// dbUsername root
	// dbPassword example
	// dbUrl jdbc:mysql://localhost:3306/db
	// name foo
	// noOfBoughtGoods 15
	// Occupation EMPLOYED

	public static void main(String[] args) {
		if (args.length != 6) {
			throw new IllegalArgumentException("Wrong number of arguments " + Arrays.toString(args) + ". There must be exactly 6 arguments");
		}
		String dbUsername = args[0];
		String dbPassword = args[1];
		String dbUrl = args[2];
		String personName = args[3];
		String personNoOfGoods = args[4];
		String personOccupation = args[5];
		// For simplicity's sake we're fixing most values
		new Week2Part5(dbUsername, dbPassword, dbUrl).calculateDiscount(new Person(personName, Integer.parseInt(personNoOfGoods), Occupation.valueOf(personOccupation)));
		System.exit(0);
	}

	Discount calculateDiscount(Person person) {
		List<DiscountApplier> appliers = Arrays.asList(new OccupationDiscountApplier(new DatabaseRateRepository(user, password, databaseUrl)), new NoOfBoughtGoodsDiscountApplier(), new NameDiscountApplier());
		DiscountCalculator discountCalculator = new DiscountCalculator(appliers);
		log.info("Will calculate the total discount");
		Discount totalDiscount = discountCalculator.calculateTotalDiscountRate(person);
		log.info("Total discount rate for person [{}] is equal to [{}]", person.getName(), totalDiscount.getRate());
		return totalDiscount;
	}
}
