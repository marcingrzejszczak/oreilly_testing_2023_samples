package com.example.week3.part4.done;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Week3Part4 {
	private static final Logger log = Logger.getLogger(Week3Part4.class.getName());

	// name noOfBoughtGoods Occupation
	// foo 15 EMPLOYED
	// bar 5 UNEMPLOYED
	public static void main(String[] args) {
		if (args.length != 3) {
			throw new IllegalArgumentException("Wrong number of arguments " + Arrays.toString(args) + ". There must be exactly 3 arguments");
		}
		Person person = new Person(args[0], Integer.parseInt(args[1]), Occupation.valueOf(args[2]));
		new Week3Part4().calculateDiscount(person);
		System.exit(0);
	}

	Discount calculateDiscount(Person person) {
		List<DiscountApplier> appliers = getAppliers();
		DiscountCalculator discountCalculator = new DiscountCalculator(appliers);
		Discount totalDiscount = discountCalculator.calculateTotalDiscountRate(person);
		log.log(Level.INFO, () -> "Total discount rate for person [" + person + "] is equal to [" + totalDiscount.getRate() + "]");
		return totalDiscount;
	}

	private List<DiscountApplier> getAppliers() {
		return Arrays.asList(new OccupationDiscountApplier(), new NoOfBoughtGoodsDiscountApplier(), new NameDiscountApplier());
	}
}
