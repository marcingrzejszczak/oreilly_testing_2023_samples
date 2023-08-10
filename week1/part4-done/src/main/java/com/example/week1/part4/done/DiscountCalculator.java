package com.example.week1.part4.done;

import java.io.Closeable;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiscountCalculator implements Closeable {

	private static final Logger log = LoggerFactory.getLogger(DiscountCalculator.class);

	private final List<DiscountApplier> discountAppliers;

	private final ExecutorService executorService = Executors.newCachedThreadPool();

	private final MessageSender messageSender;

	DiscountCalculator(List<DiscountApplier> discountAppliers, MessageSender messageSender) {
		this.discountAppliers = discountAppliers;
		this.messageSender = messageSender;
	}

	void calculateTotalDiscount(Person person) {
		log.info("Calculating total discount from {} appliers", discountAppliers.stream().map(DiscountApplier::getName).toList());
		this.discountAppliers
				.forEach(discountApplier -> CompletableFuture.runAsync(() -> {
					log.info("Will calculate rate via {}", discountApplier.getName());
					messageSender.sendMessage(new Message(EventType.TASK_DISCOUNT_CALCULATION_SUBMITTED, discountApplier.getName()));
					Discount discountRate = discountApplier.getDiscountRate(person);
					person.addDiscount(discountRate);
				}, executorService).exceptionally(throwable -> {
					log.error("Exception occurred while trying to calculate rate", throwable);
					messageSender.sendMessage(new Message(EventType.TASK_DISCOUNT_CALCULATION_ERRORED, discountApplier.getName()));
					person.addDiscount(new Discount("error", 0D));
					return null;
				}).thenAccept(unused -> {
					messageSender.sendMessage(new Message(EventType.TASK_DISCOUNT_CALCULATION_FINISHED, discountApplier.getName()));
					log.info("Calculated rate via {}", discountApplier.getName());
				}));
	}

	@Override
	public void close() {
		this.executorService.shutdown();
	}

}
