package com.example.week3.part1.done.repository;

import com.example.week3.part1.done.model.Occupation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class OccupationUtils {
	private static final Logger log = LoggerFactory.getLogger(OccupationUtils.class);

	private OccupationUtils() {
		throw new IllegalStateException("Can't instantiate utility class");
	}

	static double getRateForOccupation(Occupation occupation) {
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
