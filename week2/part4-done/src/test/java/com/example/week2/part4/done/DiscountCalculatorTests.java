package com.example.week2.part4.done;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.week2.part4.done.assertion.PersonAssert.then;

@ExtendWith(MockitoExtension.class)
class DiscountCalculatorTests {

	@Mock
	MessageSender messageSender;

	@Test
	void should_calculate_no_discount_when_no_appliers_present() {
		DiscountCalculator discountCalculator = new DiscountCalculator(messageSender, Collections.emptyList());
		Person person = person();

		discountCalculator.calculateTotalDiscountRate(person);

		then(person).doesNotHaveDiscount();
	}

	@Test
	void should_calculate_total_discount_when_appliers_present() {
		double fixedDiscountRate1 = 5D;
		double fixedDiscountRate2 = 7D;
		DiscountCalculator discountCalculator = new DiscountCalculator(messageSender, Arrays.asList(person -> person.setDiscountRate(person.getDiscountRate() + fixedDiscountRate1), person -> person.setDiscountRate(person.getDiscountRate() + fixedDiscountRate2)));
		Person person = person();

		discountCalculator.calculateTotalDiscountRate(person);

		then(person).hasDiscountEqualTo(fixedDiscountRate1 + fixedDiscountRate2);
	}

	@Test
	void should_send_message_after_calculating_discounts() {
		double fixedDiscountRate = 5D;
		DiscountCalculator discountCalculator = new DiscountCalculator(messageSender, List.of(person -> person.setDiscountRate(person.getDiscountRate() + fixedDiscountRate)));
		Person person = person();

		discountCalculator.calculateTotalDiscountRate(person);

		BDDMockito.then(messageSender).should().sendMessage(person);
	}

	private static Person person() {
		return new Person("test", 1, Occupation.UNEMPLOYED);
	}
}
