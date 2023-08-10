package com.example.week2.part3.done;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class DiscountController {
	private final DiscountCalculator discountCalculator;

	DiscountController(DiscountCalculator discountCalculator) {
		this.discountCalculator = discountCalculator;
	}

	// TODO: Check if validation works fine (non blank name) - bad request
	@PostMapping("/discount")
	DiscountResponse discount(@Valid @RequestBody Person person) {
		// TODO: Check if custom error mapping works fine
		if (person.getNumberOfBoughtGoods() <= 0) {
			throw new NoBoughtGoodsException(person);
		}
		this.discountCalculator.calculateTotalDiscountRate(person);
		return new DiscountResponse(person.getName(), person.getDiscountRate());
	}
}
