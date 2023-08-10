package com.example.week1.part3.done;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

import static com.example.week1.part3.done.assertion.PersonAssert.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class OccupationDiscountApplierTests {

	private static final double FIXED_RATE_FROM_DB = 10D;

	RateRepository rateRepository = mock();

	MessageSender messageSender = mock();

	@BeforeEach
	void setup() {
		given(rateRepository.getDiscountRate(any())).willReturn(FIXED_RATE_FROM_DB);
	}

	@Test
	void should_emit_discount_calculated_event_when_discount_calculation_received() {
		OccupationDiscountApplier applier = new OccupationDiscountApplier(rateRepository, messageSender);

		applier.applyDiscount(employedPerson());

		then(messageSender).should().sendMessage(new Message(EventType.DISCOUNT_CALCULATION_RECEIVED));
	}

	@Test
	void should_emit_error_event_when_error_occurred_on_repository_access() {
		given(rateRepository.getDiscountRate(any())).willThrow(new RuntimeException("BOOM!"));
		OccupationDiscountApplier applier = new OccupationDiscountApplier(rateRepository, messageSender);

		applier.applyDiscount(employedPerson());

		then(messageSender).should().sendMessage(new Message(EventType.ERROR));
	}

	@Test
	void should_not_make_any_message_calls_for_unemployed() {
		given(rateRepository.getDiscountRate(any())).willThrow(new RuntimeException("BOOM!"));
		OccupationDiscountApplier applier = new OccupationDiscountApplier(rateRepository, messageSender);

		applier.applyDiscount(unemployedPerson());

		then(messageSender).shouldHaveNoInteractions();
	}

	@Test
	void should_calculate_a_discount_rate_from_a_repository() {
		OccupationDiscountApplier applier = new OccupationDiscountApplier(rateRepository, messageSender);
		Person person = employedPerson();

		applier.applyDiscount(person);

		then(person)
				.as("Discount rate should be taken directly from the repository")
				.hasDiscountEqualTo(FIXED_RATE_FROM_DB);
	}

	@Test
	void should_not_calculate_a_discount_rate_from_a_repository_for_unemployed() {
		OccupationDiscountApplier applier = new OccupationDiscountApplier(rateRepository, messageSender);
		Person person = unemployedPerson();

		applier.applyDiscount(person);

		then(person).hasDiscountEqualTo(0D);
	}

	private static Person unemployedPerson() {
		return new Person("name", 100, Occupation.UNEMPLOYED);
	}

	private static Person employedPerson() {
		return new Person("name", 100, Occupation.EMPLOYED);
	}
}
