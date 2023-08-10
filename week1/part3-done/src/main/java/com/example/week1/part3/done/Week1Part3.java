package com.example.week1.part3.done;

import java.util.Arrays;
import java.util.List;

public class Week1Part3 {

	// name noOfBoughtGoods Occupation
	// foo 15 EMPLOYED
	// bar 5 UNEMPLOYED
	public static void main(String[] args) {
		if (args.length != 3) {
			throw new IllegalArgumentException("Wrong number of arguments " + Arrays.toString(args) + ". There must be exactly 3 arguments");
		}
		Person person = new Person(args[0], Integer.parseInt(args[1]), Occupation.valueOf(args[2]));
		new Week1Part3().calculateDiscount(person);
		System.exit(0);
	}

	void calculateDiscount(Person person) {
		MessageSender messageSender = message -> System.out.println("Sending a message [" + message + "]");
		List<DiscountApplier> appliers = Arrays.asList(new OccupationDiscountApplier(new DatabaseRateRepository(), messageSender), new NoOfBoughtGoodsDiscountApplier(messageSender), new NameDiscountApplier(messageSender));
		DiscountCalculator discountCalculator = new DiscountCalculator(appliers);
		discountCalculator.calculateTotalDiscountRate(person);
		System.out.println("Total discount rate for person [" + person + "] is equal to [" + person.getDiscountRate() + "]");
	}
}
