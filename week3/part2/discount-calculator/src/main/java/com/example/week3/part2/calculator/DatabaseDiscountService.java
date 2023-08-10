package com.example.week3.part2.calculator;

import java.util.Random;

class DatabaseDiscountService implements DiscountService {

	private final Random random = new Random();

	@Override
	public void applyDiscount(Person person) {
		try {
			Thread.sleep(5000); // simulating database access
			person.setDiscountRate(random.nextDouble());
		}
		catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
