package com.example.week3.part2.calculator;

import io.javalin.Javalin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseClass {

	DiscountHandler discountHandler; // TODO: Fix me - STUB ACTUAL DISCOUNT SERVICE LOGIC!

	NoBoughtGoodsExceptionHandler exceptionHandler = new NoBoughtGoodsExceptionHandler();

	int port = 0;

	Javalin javalin = new Week2Part3DiscountCalculator(discountHandler, exceptionHandler, port).configureJavalin();

	@BeforeEach
	void setup() {
		javalin.start(port); // Start the Server
		// TODO: Fix me: Tell Rest-Assured which port it should run on
	}

	@AfterEach
	void clean() {
		javalin.stop();
	} // Stop the Server
}

