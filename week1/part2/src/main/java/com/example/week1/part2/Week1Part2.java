package com.example.week1.part2;

import java.util.Arrays;
import java.util.List;

public class Week1Part2 {

	// name noOfBoughtGoods Occupation
	// foo 15 EMPLOYED
	// bar 5 UNEMPLOYED
	public static void main(String[] args) {
		if (args.length != 3) {
			throw new IllegalArgumentException("Wrong number of arguments " + Arrays.toString(args) + ". There must be exactly 3 arguments");
		}
		List<DiscountApplier> appliers = Arrays.asList(new OccupationDiscountApplier(), new NoOfBoughtGoodsDiscountApplier(), new NameDiscountApplier());
		DiscountCalculator discountCalculator = new DiscountCalculator();
		ReflectionUtils.injectDependency(DiscountCalculator.class, "discountAppliers", discountCalculator, appliers);
		Person person = new Person(args[0], Integer.parseInt(args[1]), Occupation.valueOf(args[2]));
		Discount totalDiscount = discountCalculator.calculateTotalDiscountRate(person);
		System.out.println("Total discount rate for person [" + person + "] is equal to [" + totalDiscount.getRate() + "]");
		System.exit(0);
	}
}
