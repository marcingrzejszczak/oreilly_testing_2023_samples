package com.example.week1.part4.done;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.week1.part4.done.assertion.DiscountAssert.then;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.argThat;

@ExtendWith(MockitoExtension.class)
class DiscountCalculatorTests {

	@Mock
	MessageSender messageSender;

	@Test
	void should_calculate_no_discount_when_no_appliers_present() {
		try (DiscountCalculator discountCalculator = new DiscountCalculator(Collections.emptyList(), messageSender)) {
			Person person = person();

			discountCalculator.calculateTotalDiscount(person);

			Awaitility.await()
					// Give it a chance to run
					.pollDelay(50, TimeUnit.MILLISECONDS)
					.untilAsserted(() -> {
						then(person.getTotalDiscount()).isNotSet();
						BDDMockito.then(messageSender).shouldHaveNoInteractions();
					});
		}
	}

	@Test
	void should_calculate_total_discount_when_appliers_present() {
		double fixedDiscountRate1 = 5D;
		double fixedDiscountRate2 = 7D;
		try (DiscountCalculator discountCalculator = new DiscountCalculator(Arrays.asList(fixedDiscountApplier(fixedDiscountRate1, "first"), fixedDiscountApplier(fixedDiscountRate2, "second")), messageSender)) {
			Person person = person();

			discountCalculator.calculateTotalDiscount(person);

			Awaitility.await().untilAsserted(() -> {
				List<Discount> discounts = person.getDiscounts();
				then(discounts).hasSize(2);
				then(discounts.stream().map(Discount::getApplierName).toList())
						.as("Appliers should be called in any order")
						.containsOnly("first", "second");
				then(discounts.stream().map(Discount::getRate).reduce(0D, Double::sum))
						.as("Total discount rate must be correct")
						.isEqualTo(fixedDiscountRate1 + fixedDiscountRate2);
			});
		}
	}

	@Test
	void should_add_a_zero_error_discount_when_exception_happened() {
		try (DiscountCalculator discountCalculator = new DiscountCalculator(Collections.singletonList(errorThrowingDiscountApplier()), messageSender)) {
			Person person = person();

			discountCalculator.calculateTotalDiscount(person);

			Awaitility.await().untilAsserted(() -> {
				List<Discount> discounts = person.getDiscounts();
				then(discounts).hasSize(1);
				then(discounts.stream().map(Discount::getApplierName).toList())
						.as("There should be only a single error discount")
						.containsOnly("error");
				then(discounts.stream().map(Discount::getRate).reduce(0D, Double::sum))
						.as("Total discount rate must be 0")
						.isEqualTo(0D);
			});
		}
	}

	@Test
	void should_emit_calculation_submit_events_when_appliers_present() {
		double fixedDiscountRate1 = 5D;
		double fixedDiscountRate2 = 7D;
		try (DiscountCalculator discountCalculator = new DiscountCalculator(Arrays.asList(fixedDiscountApplier(fixedDiscountRate1, "first"), fixedDiscountApplier(fixedDiscountRate2, "second")), messageSender)) {

			discountCalculator.calculateTotalDiscount(person());

			// If we don't care about order of events
			Awaitility.await().untilAsserted(() -> {
				BDDMockito.then(messageSender).should().sendMessage(argThat(argument -> argument.eventType == EventType.TASK_DISCOUNT_CALCULATION_SUBMITTED && "first".equals(argument.message)));
				BDDMockito.then(messageSender).should().sendMessage(argThat(argument -> argument.eventType == EventType.TASK_DISCOUNT_CALCULATION_FINISHED && "first".equals(argument.message)));
				BDDMockito.then(messageSender).should().sendMessage(argThat(argument -> argument.eventType == EventType.TASK_DISCOUNT_CALCULATION_SUBMITTED && "second".equals(argument.message)));
				BDDMockito.then(messageSender).should().sendMessage(argThat(argument -> argument.eventType == EventType.TASK_DISCOUNT_CALCULATION_FINISHED && "second".equals(argument.message)));
			});
		}
	}

	@Test
	void should_emit_calculation_submit_events_when_appliers_present_and_order_matters() {
		double fixedDiscountRate1 = 5D;
		double fixedDiscountRate2 = 7D;
		NameRememberingMessageSender messageSender = new NameRememberingMessageSender();
		try (DiscountCalculator discountCalculator = new DiscountCalculator(Arrays.asList(fixedDiscountApplier(fixedDiscountRate1, "first"), fixedDiscountApplier(fixedDiscountRate2, "second")), messageSender)) {

			discountCalculator.calculateTotalDiscount(person());

			// If we care about order
			Awaitility.await().untilAsserted(() -> {
				then(messageSender.eventsForApplier("first")).containsExactly(EventType.TASK_DISCOUNT_CALCULATION_SUBMITTED, EventType.TASK_DISCOUNT_CALCULATION_FINISHED);
				then(messageSender.eventsForApplier("second")).containsExactly(EventType.TASK_DISCOUNT_CALCULATION_SUBMITTED, EventType.TASK_DISCOUNT_CALCULATION_FINISHED);
			});
		}
	}

	private static DiscountApplier errorThrowingDiscountApplier() {
		return new DiscountApplier() {
			@Override
			public Discount getDiscountRate(Person person) {
				throw new RuntimeException("BOOM!");
			}

			@Override
			public String getName() {
				return "exception";
			}
		};
	}

	private static DiscountApplier fixedDiscountApplier(double fixedDiscountRate, String name) {
		return new DiscountApplier() {
			@Override
			public Discount getDiscountRate(Person person) {
				Latency.simulateRandomLatency(10, 50);
				return new Discount(getName(), fixedDiscountRate);
			}

			@Override
			public String getName() {
				return name;
			}
		};
	}

	private static Person person() {
		return new Person("test", 1, Occupation.UNEMPLOYED);
	}

	/**
	 * Example of a message sender mock that stores events for a given discount applier
	 */
	static class NameRememberingMessageSender implements MessageSender {

		private final Map<String, List<EventType>> messages = new ConcurrentHashMap<>();

		@Override
		public void sendMessage(Message message) {
			List<EventType> events = messages.computeIfAbsent(message.message, s -> new CopyOnWriteArrayList<>());
			events.add(message.eventType);
		}

		List<EventType> eventsForApplier(String applier) {
			return messages.getOrDefault(applier, new ArrayList<>());
		}
	}
}
