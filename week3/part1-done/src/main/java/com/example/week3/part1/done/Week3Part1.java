package com.example.week3.part1.done;

import java.util.Arrays;
import java.util.List;

import com.example.week3.part1.done.applier.DiscountApplier;
import com.example.week3.part1.done.model.Discount;
import com.example.week3.part1.done.model.Occupation;
import com.example.week3.part1.done.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Week3Part1 {

	private static final Logger log = LoggerFactory.getLogger(Week3Part1.class);

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
		log.info("Total discount rate for person [{}] is equal to [{}]", person, totalDiscount.getRate());
		return totalDiscount;
	}
}
