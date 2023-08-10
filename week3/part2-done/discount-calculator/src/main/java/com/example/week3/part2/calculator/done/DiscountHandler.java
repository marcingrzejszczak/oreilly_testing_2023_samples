package com.example.week3.part2.calculator.done;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DiscountHandler implements Handler {

	private static final Logger log = LoggerFactory.getLogger(DiscountHandler.class);

	private final DiscountService discountService;

	DiscountHandler(DiscountService discountService) {
		this.discountService = discountService;
	}

	@Override
	public void handle(@NotNull Context context) throws Exception {
		Person person = context.bodyAsClass(Person.class);
		DiscountResponse discountResponse = discount(person);
		context.json(discountResponse);
	}

	private DiscountResponse discount(Person person) {
		if (person.getNumberOfBoughtGoods() <= 0) {
			throw new NoBoughtGoodsException(person);
		}
		this.discountService.applyDiscount(person);
		DiscountResponse discountResponse = new DiscountResponse(person.getName(), person.getDiscountRate());
		log.info("Returning discount [{}]", discountResponse);
		return discountResponse;
	}
}
