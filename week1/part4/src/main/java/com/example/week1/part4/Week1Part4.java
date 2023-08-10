package com.example.week1.part4;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Week1Part4 {

	private static final Logger log = LoggerFactory.getLogger(Week1Part4.class);

	// name noOfBoughtGoods Occupation
	// foo 15 EMPLOYED
	// bar 5 UNEMPLOYED
	public static void main(String[] args) {
		if (args.length != 3) {
			throw new IllegalArgumentException("Wrong number of arguments " + Arrays.toString(args) + ". There must be exactly 3 arguments");
		}
		Person person = new Person(args[0], Integer.parseInt(args[1]), Occupation.valueOf(args[2]));
		new Week1Part4().calculateDiscount(person);
		System.exit(0);
	}

	Discount calculateDiscount(Person person) {
		log.info("Calculating discount for person [{}]", person);
		CountingMessageSender messageSender = new CountingMessageSender();
		List<DiscountApplier> appliers = Arrays.asList(new OccupationDiscountApplier(new DatabaseRateRepository(), messageSender), new NoOfBoughtGoodsDiscountApplier(messageSender), new NameDiscountApplier(messageSender));
		final CountDownLatch latch = new CountDownLatch(appliers.size());
		messageSender.setLatch(latch);
		try (DiscountCalculator discountCalculator = new DiscountCalculator(appliers, messageSender)) {
			discountCalculator.calculateTotalDiscount(person);
			int timeout = 50;
			TimeUnit unit = TimeUnit.SECONDS;
			log.info("Waiting for [{}] {} for calculations to complete", timeout, unit);
			boolean finished = latch.await(timeout, unit);
			if (!finished) {
				log.warn("Not all discounts got calculated!");
			}
			List<Discount> discounts = person.getDiscounts();
			log.info("Found the following discounts {}", discounts);
			Double totalDiscount = discounts.stream()
					.map(Discount::getRate).reduce(0D, Double::sum);
			log.info("Total discount rate for person [{}] is equal to [{}]", person, totalDiscount);
			return new Discount("total", totalDiscount);
		}
		catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	static class CountingMessageSender implements MessageSender {
		private CountDownLatch latch;

		@Override
		public void sendMessage(Message message) {
			log.info("Sending a message [{}]", message);
			if (message.eventType == EventType.TASK_DISCOUNT_CALCULATION_FINISHED) {
				Objects.requireNonNull(latch).countDown();
			}
		}

		void setLatch(CountDownLatch latch) {
			this.latch = latch;
		}
	}
}
