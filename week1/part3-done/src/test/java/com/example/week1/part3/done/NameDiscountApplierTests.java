package com.example.week1.part3.done;

import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.week1.part3.done.assertion.PersonAssert.then;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class NameDiscountApplierTests {

	@Mock MessageSender messageSender;

	@Test
	void should_emit_discount_calculated_event_when_discount_calculated() {
		NameDiscountApplier applier = new NameDiscountApplier(messageSender);
		Person person = personWithNameLengthAboveThreshold();

		applier.applyDiscount(person);

		BDDMockito.then(messageSender).should().sendMessage(new Message(EventType.DISCOUNT_CALCULATED));
		then(person.getDiscountRate()).as("Discount got calculated").isNotZero();
	}

	@Test
	void should_calculate_a_discount_rate_when_person_has_a_long_enough_name() {
		NameDiscountApplier applier = new NameDiscountApplier(messageSender);
		Person person = personWithNameLengthAboveThreshold();

		applier.applyDiscount(person);

		then(person).hasDiscountEqualTo(NameDiscountApplier.DISCOUNT_RATE);
	}

	@Test
	void should_calculate_a_discount_rate_when_person_has_no_of_goods_equal_to_threshold() {
		NameDiscountApplier applier = new NameDiscountApplier(messageSender);
		Person person = personWithNameLengthEqualToThreshold();

		applier.applyDiscount(person);

		then(person).hasDiscountEqualTo(NameDiscountApplier.DISCOUNT_RATE);
	}

	@Test
	void should_calculate_no_discount_rate_when_person_has_no_of_goods_below_threshold() {
		NameDiscountApplier applier = new NameDiscountApplier(messageSender);
		Person person = personWithNameLengthBelowThreshold();

		applier.applyDiscount(person);

		then(person).doesNotHaveDiscount();
	}

	// TODO: Example of a parametrized test
	/**
	 * Check {@link NameDiscountApplier#THRESHOLD} for threshold value
	 * @param name person name
	 * @param expectedDiscountRate expected discount rate
	 */
	@ParameterizedTest(name = "[{index}] For a name <{0}> expected rate is <{1}>")
	@CsvSource({"nam,8", "name above threshold,8", "na,0"})
	void should_calculate_rate_based_on_name(String name, double expectedDiscountRate) {
		NameDiscountApplier applier = new NameDiscountApplier(messageSender);
		Person person = personWithName(name);

		applier.applyDiscount(person);

		then(person).hasDiscountEqualTo(expectedDiscountRate);
	}

	private static Person personWithNameLengthEqualToThreshold() {
		return new Person("nam", 0, Occupation.UNEMPLOYED);
	}

	private static Person personWithNameLengthAboveThreshold() {
		return new Person("long enough name", 0, Occupation.UNEMPLOYED);
	}

	private static Person personWithNameLengthBelowThreshold() {
		return new Person("na", 0, Occupation.UNEMPLOYED);
	}

	private static Person personWithName(String name) {
		return new Person(name, 0, Occupation.UNEMPLOYED);
	}
}
