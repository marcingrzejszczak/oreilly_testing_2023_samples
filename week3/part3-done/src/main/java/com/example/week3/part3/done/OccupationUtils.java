package com.example.week3.part3.done;

import java.util.logging.Level;
import java.util.logging.Logger;

class OccupationUtils {

	private static final Logger log = Logger.getLogger(OccupationUtils.class.getName());

	public static double getRateForOccupation(Occupation occupation) {
		log.log(Level.INFO, "Connecting to the database... please wait");
		// Simulating database connection
		// ...
		if (occupation == null || occupation == Occupation.UNEMPLOYED) {
			return 0D;
		}
		return 10D;
	}

}
