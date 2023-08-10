package com.example.week2.part2.done;

import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Week2Part2 {

	// name noOfBoughtGoods Occupation
	// foo 15 EMPLOYED
	// bar 5 UNEMPLOYED
	public static void main(String[] args) {
		if (args.length != 3) {
			throw new IllegalArgumentException("Wrong number of arguments " + Arrays.toString(args) + ". There must be exactly 3 arguments");
		}
		Person person = new Person(args[0], Integer.parseInt(args[1]), Occupation.valueOf(args[2]));
		new Week2Part2().calculateDiscount(person);
		System.exit(0);
	}

	Discount calculateDiscount(Person person) {
		// Setup
		ApplicationContext context =
				new AnnotationConfigApplicationContext(DiscountConfiguration.class);
		DiscountCalculator discountCalculator =
				context.getBean(DiscountCalculator.class);

		// Business logic
		Discount discount = discountCalculator.calculateTotalDiscountRate(person);

		System.out.println("Total discount rate for person [" + person + "] "
				+ "is equal to [" + discount.getRate() + "]");
		return discount;
	}
}
