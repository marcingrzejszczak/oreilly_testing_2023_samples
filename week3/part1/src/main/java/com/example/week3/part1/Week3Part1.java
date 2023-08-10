package com.example.week3.part1;

import java.util.Arrays;
import java.util.List;

import com.example.week3.part1.applier.DiscountApplier;
import com.example.week3.part1.model.Discount;
import com.example.week3.part1.model.Occupation;
import com.example.week3.part1.model.Person;

public class Week3Part1 {

	// name noOfBoughtGoods Occupation
	// foo 15 EMPLOYED
	// bar 5 UNEMPLOYED
	public static void main(String[] args) {
		if (args.length != 3) {
			throw new IllegalArgumentException("Wrong number of arguments " + Arrays.toString(args) + ". There must be exactly 3 arguments");
		}
		Person person = new Person(args[0], Integer.parseInt(args[1]), Occupation.valueOf(args[2]));
		new Week3Part1().calculateDiscount(person);
		System.exit(0);
	}

	Discount calculateDiscount(Person person) {
		List<DiscountApplier> appliers = DiscountApplier.defaultAppliers();
		DiscountCalculator discountCalculator = new DiscountCalculator(appliers);
		Discount totalDiscount = discountCalculator.calculateTotalDiscountRate(person);
		System.out.println("Total discount rate for person [" + person + "] is equal to [" + totalDiscount.getRate() + "]");
		return totalDiscount;
	}
}
