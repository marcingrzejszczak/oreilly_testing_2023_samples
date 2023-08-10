package com.example.week2.part4.done;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.week2.part4.done.assertion.PersonAssert.then;
import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
class NameDiscountApplierTests {

	@Mock MessageSender messageSender;

	int nameThreshold = 3;

	double discountRate = 8D;

	@Test
	void should_emit_discount_calculated_event_when_discount_calculated() {
		NameDiscountApplier applier = new NameDiscountApplier(nameThreshold, discountRate, messageSender);
		Person person = personWithNameLengthAboveThreshold();

		applier.applyDiscount(person);

		BDDMockito.then(messageSender).should().sendMessage(new Message(EventType.DISCOUNT_CALCULATED));
		then(person.getDiscountRate()).as("Discount got calculated").isNotZero();
	}

	@ParameterizedTest(name = "[{index}] For a name <{0}> expected rate is <{1}>")
	@CsvSource({"nam,8", "name above threshold,8", "na,0"})
	void should_calculate_rate_based_on_name(String name, double expectedDiscountRate) {
		NameDiscountApplier applier = new NameDiscountApplier(nameThreshold, discountRate, messageSender);
		Person person = personWithName(name);

		applier.applyDiscount(person);

		then(person).hasDiscountEqualTo(expectedDiscountRate);
	}

	private static Person personWithNameLengthAboveThreshold() {
		return new Person("long enough name", 0, Occupation.UNEMPLOYED);
	}

	private static Person personWithName(String name) {
		return new Person(name, 0, Occupation.UNEMPLOYED);
	}
}
