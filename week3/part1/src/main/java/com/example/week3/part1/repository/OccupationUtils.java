package com.example.week3.part1.repository;

import com.example.week3.part1.model.Occupation;

public class OccupationUtils {

	public static double getRateForOccupation(Occupation occupation) {
		try {
			System.out.println("Connecting to the database... please wait");
			// Simulating database connection
			Thread.sleep(10_000);
			System.out.println("Database query completed");
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		if (occupation == Occupation.UNEMPLOYED) {
			return 0D;
		}
		return 10D;
	}

}
