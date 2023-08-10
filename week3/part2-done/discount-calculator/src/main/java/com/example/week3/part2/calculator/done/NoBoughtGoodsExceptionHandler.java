package com.example.week3.part2.calculator.done;

import io.javalin.http.Context;
import io.javalin.http.ExceptionHandler;
import io.javalin.http.HttpStatus;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class NoBoughtGoodsExceptionHandler implements ExceptionHandler<NoBoughtGoodsException> {

	private static final Logger log = LoggerFactory.getLogger(NoBoughtGoodsExceptionHandler.class);

	@Override
	public void handle(@NotNull NoBoughtGoodsException ex, @NotNull Context context) {
		CustomResponse customResponse = new CustomResponse(ex.getPerson());
		log.info("Setting custom response [{}]", customResponse);
		context.json(customResponse).status(HttpStatus.BAD_REQUEST);
	}
}
