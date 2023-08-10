package com.example.week3.part2.calculator;

import java.util.Arrays;

import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;

public class Week2Part3DiscountCalculator {

	// port
	// 0 - random
	// 80 - fixed
	public static void main(String[] args) {
		if (args.length != 1) {
			throw new IllegalArgumentException("Wrong number of arguments " + Arrays.toString(args) + ". There must be exactly 1 arguments");
		}
		int port = Integer.parseInt(args[0]);
		DiscountHandler discountHandler = new DiscountHandler(new DatabaseDiscountService());
		NoBoughtGoodsExceptionHandler exceptionHandler = new NoBoughtGoodsExceptionHandler();
		new Week2Part3DiscountCalculator(discountHandler, exceptionHandler, port).run();
	}

	private final DiscountHandler discountHandler;

	private final ExceptionHandler<NoBoughtGoodsException> exceptionHandler;

	private final int port;

	public Week2Part3DiscountCalculator(DiscountHandler discountHandler,
			ExceptionHandler<NoBoughtGoodsException> exceptionHandler, int port) {
		this.discountHandler = discountHandler;
		this.exceptionHandler = exceptionHandler;
		this.port = port;
	}

	void run() {
		try (Javalin javalin = configureJavalin().start(this.port)) {
			// waiting for requests
			while (true) {

			}
		}
	}

	Javalin configureJavalin() {
		return Javalin.create()
				.post("/discount", this.discountHandler)
				.exception(NoBoughtGoodsException.class, this.exceptionHandler);
	}

}
