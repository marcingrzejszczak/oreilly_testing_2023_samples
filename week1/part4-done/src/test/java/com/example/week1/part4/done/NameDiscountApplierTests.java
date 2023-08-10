package com.example.week1.part4.done;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.week1.part4.done.assertion.DiscountAssert.then;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;


@ExtendWith(MockitoExtension.class)
class NameDiscountApplierTests {

	@Mock
	MessageSender messageSender;

	@Test
	void should_have_a_non_empty_applier_name() {
		then(new NameDiscountApplier(null).getName()).isNotEmpty();
	}

	@Test
	void should_throw_exception_when_null_person() {
		NameDiscountApplier applier = new NameDiscountApplier(messageSender);

		thenThrownBy(() -> applier.getDiscountRate(null))
				.isInstanceOf(InvalidPersonException.class)
						.hasMessage("Person can't be null");
	}

	@Test
	void should_emit_discount_calculated_event_when_discount_calculated() {
		NameDiscountApplier applier = new NameDiscountApplier(messageSender);
		Person person = personWithNameLengthAboveThreshold();

		Discount discount = applier.getDiscountRate(person);

		BDDMockito.then(messageSender).should().sendMessage(new Message(EventType.DISCOUNT_CALCULATED));
		then(discount.getRate()).as("Discount got calculated").isNotZero();
	}

	@Test
	void should_return_a_discount_rate_when_person_has_a_long_enough_name() {
		NameDiscountApplier applier = new NameDiscountApplier(messageSender);

		Discount discount = applier.getDiscountRate(personWithNameLengthAboveThreshold());

		then(discount).isEqualTo(NameDiscountApplier.DISCOUNT_RATE);
	}

	@Test
	void should_return_a_discount_rate_when_person_has_no_of_goods_equal_to_threshold() {
		NameDiscountApplier applier = new NameDiscountApplier(messageSender);

		Discount discount = applier.getDiscountRate(personWithNameLengthEqualToThreshold());

		then(discount).isEqualTo(NameDiscountApplier.DISCOUNT_RATE);
	}

	@Test
	void should_return_no_discount_rate_when_person_has_no_of_goods_below_threshold() {
		NameDiscountApplier applier = new NameDiscountApplier(messageSender);

		Discount discount = applier.getDiscountRate(personWithNameLengthBelowThreshold());

		then(discount).isNotSet();
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

		Discount discount = applier.getDiscountRate(personWithName(name));

		then(discount).isEqualTo(expectedDiscountRate);
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
