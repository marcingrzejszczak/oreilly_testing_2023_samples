package com.example.week1.part4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Write an integration test for this
public class OccupationUtils {

	private static final Logger log = LoggerFactory.getLogger(OccupationUtils.class);

	public static double getRateForOccupation(Occupation occupation) {
		try {
			log.info("Connecting to the database... please wait");
			// Simulating database connection
			Thread.sleep(10_000);
			log.info("Database query completed");
		}
		catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		if (occupation == Occupation.UNEMPLOYED) {
			return 0D;
		}
		return 10D;
	}

}
