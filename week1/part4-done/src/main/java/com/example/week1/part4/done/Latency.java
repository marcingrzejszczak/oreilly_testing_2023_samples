package com.example.week1.part4.done;

import java.util.Random;

public class Latency {

	private static final Random random = new Random();

	public static void simulateRandomLatency() {
		simulateRandomLatency(100, 200);
	}

	public static void simulateRandomLatency(int min, int randomPart) {
		try {
			Thread.sleep(min + random.nextInt(randomPart));
		}
		catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
