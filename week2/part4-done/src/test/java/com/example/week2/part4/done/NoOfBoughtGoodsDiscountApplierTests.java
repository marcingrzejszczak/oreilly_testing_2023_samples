package com.example.week2.part4.done;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.BDDMockito;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.week2.part4.done.assertion.PersonAssert.then;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.argThat;

@ExtendWith(MockitoExtension.class)
class NoOfBoughtGoodsDiscountApplierTests {

	@Mock MessageSender messageSender;

	int goodsThreshold = 5;

	double discountRate = 5D;

	@Test
	void should_first_emit_discount_calculation_received_and_then_discount_calculated_event_when_discount_calculated() {
		NoOfBoughtGoodsDiscountApplier applier = new NoOfBoughtGoodsDiscountApplier(goodsThreshold, discountRate, messageSender);
		Person person = personWithNumberOfGoodsAboveThreshold(goodsThreshold);

		applier.applyDiscount(person);

		InOrder inOrder = BDDMockito.inOrder(messageSender);
		inOrder.verify(messageSender).sendMessage(argThat(argument -> argument instanceof Message && ((Message) argument).getEventType() == EventType.DISCOUNT_CALCULATION_RECEIVED));
		inOrder.verify(messageSender).sendMessage(argThat(argument -> argument instanceof Message && ((Message) argument).getEventType() == EventType.DISCOUNT_CALCULATED));
		then(person.getDiscountRate()).as("Discount got calculated").isNotZero();
	}

	@ParameterizedTest(name = "[{index}] For no of goods <{0}> expected rate is <{1}>")
	@CsvSource({"5,5", "6,5", "4,0"})
	void should_calculate_rate_based_on_number_of_goods(int noOfGoods, double expectedDiscountRate) {
		NoOfBoughtGoodsDiscountApplier applier = new NoOfBoughtGoodsDiscountApplier(goodsThreshold, discountRate, messageSender);
		Person person = personWithNoOfGoods(noOfGoods);

		applier.applyDiscount(person);

		then(person).hasDiscountEqualTo(expectedDiscountRate);
	}

	private static Person personWithNumberOfGoodsAboveThreshold(int threshold) {
		return new Person("name", threshold + 1, Occupation.UNEMPLOYED);
	}

	private static Person personWithNoOfGoods(int noOfGoods) {
		return new Person("name", noOfGoods, Occupation.UNEMPLOYED);
	}
}
