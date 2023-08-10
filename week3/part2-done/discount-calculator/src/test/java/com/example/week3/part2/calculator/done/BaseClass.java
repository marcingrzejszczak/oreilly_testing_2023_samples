package com.example.week3.part2.calculator.done;

import io.javalin.Javalin;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseClass {

	// STUB ACTUAL DISCOUNT SERVICE LOGIC!
	DiscountHandler discountHandler = new DiscountHandler(person -> person.setDiscountRate(10D));

	NoBoughtGoodsExceptionHandler exceptionHandler = new NoBoughtGoodsExceptionHandler();

	int port = 0;

	Javalin javalin = new Week2Part3DiscountCalculator(discountHandler, exceptionHandler, port).configureJavalin();

	@BeforeEach
	void setup() {
		javalin.start(port); // Start the Server
		RestAssured.port = javalin.port(); // Tell Rest-Assured which port it should run on
	}

	@AfterEach
	void clean() {
		javalin.stop();
	} // Stop the Server
}

