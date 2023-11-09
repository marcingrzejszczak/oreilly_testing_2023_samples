package com.example.week1.part4.done;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.BDDMockito;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.week1.part4.done.assertion.DiscountAssert.then;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenThrownBy;
import static org.mockito.ArgumentMatchers.argThat;


@ExtendWith(MockitoExtension.class)
class NoOfBoughtGoodsDiscountApplierTests {

	@Mock
	MessageSender messageSender;

	@Test
	void should_have_a_non_empty_applier_name() {
		then(new NoOfBoughtGoodsDiscountApplier(null).getName()).isNotEmpty();
	}

	@Test
	void should_throw_exception_when_null_person() {
		NoOfBoughtGoodsDiscountApplier applier = new NoOfBoughtGoodsDiscountApplier(messageSender);

		thenThrownBy(() -> applier.getDiscountRate(null))
				.isInstanceOf(InvalidPersonException.class)
				.hasMessage("Person can't be null");
	}

	@Test
	void should_first_emit_discount_calculation_received_and_then_discount_calculated_event_when_discount_calculated() {
		NoOfBoughtGoodsDiscountApplier applier = new NoOfBoughtGoodsDiscountApplier(messageSender);
		Person person = personWithNumberOfGoodsAboveThreshold();

		Discount discount = applier.getDiscountRate(person);

		InOrder inOrder = BDDMockito.inOrder(messageSender);
		inOrder.verify(messageSender).sendMessage(argThat(argument -> argument.eventType == EventType.DISCOUNT_CALCULATION_RECEIVED));
		inOrder.verify(messageSender).sendMessage(argThat(argument -> argument.eventType == EventType.DISCOUNT_CALCULATED));
		then(discount.getRate()).as("Discount got calculated").isNotZero();
	}

	@Test
	void should_return_a_discount_rate_when_person_has_no_of_goods_above_threshold() {
		NoOfBoughtGoodsDiscountApplier applier = new NoOfBoughtGoodsDiscountApplier(messageSender);

		Discount discount = applier.getDiscountRate(personWithNumberOfGoodsAboveThreshold());

		then(discount).isEqualTo(NoOfBoughtGoodsDiscountApplier.DISCOUNT_RATE);
	}

	@Test
	void should_return_a_discount_rate_when_person_has_no_of_goods_equal_to_threshold() {
		NoOfBoughtGoodsDiscountApplier applier = new NoOfBoughtGoodsDiscountApplier(messageSender);

		Discount discount = applier.getDiscountRate(personWithNumberOfGoodsEqualToThreshold());

		then(discount).isEqualTo(NoOfBoughtGoodsDiscountApplier.DISCOUNT_RATE);
	}

	@Test
	void should_return_no_discount_rate_when_person_has_no_of_goods_below_threshold() {
		NoOfBoughtGoodsDiscountApplier applier = new NoOfBoughtGoodsDiscountApplier(messageSender);

		Discount discount = applier.getDiscountRate(personWithNumberOfGoodsBelowThreshold());

		then(discount).isNotSet();
	}

	// TODO: Example of a parametrized test
	/**
	 * Check {@link NoOfBoughtGoodsDiscountApplier#THRESHOLD} for threshold value
	 * @param noOfGoods number of goods that a person bought
	 * @param expectedDiscountRate expected discount rate
	 */
	@ParameterizedTest(name = "[{index}] For no of goods <{0}> expected rate is <{1}>")
	@CsvSource({"5,5", "6,5", "4,0"})
	void should_calculate_rate_based_on_number_of_goods(int noOfGoods, double expectedDiscountRate) {
		NoOfBoughtGoodsDiscountApplier applier = new NoOfBoughtGoodsDiscountApplier(messageSender);

		Discount discount = applier.getDiscountRate(personWithNoOfGoods(noOfGoods));

		then(discount).isEqualTo(expectedDiscountRate);
	}

	private static Person personWithNumberOfGoodsEqualToThreshold() {
		return new Person("name", NoOfBoughtGoodsDiscountApplier.THRESHOLD, Occupation.UNEMPLOYED);
	}

	private static Person personWithNumberOfGoodsAboveThreshold() {
		return new Person("name", NoOfBoughtGoodsDiscountApplier.THRESHOLD + 1, Occupation.UNEMPLOYED);
	}

	private static Person personWithNumberOfGoodsBelowThreshold() {
		return new Person("name", NoOfBoughtGoodsDiscountApplier.THRESHOLD - 1, Occupation.UNEMPLOYED);
	}

	private static Person personWithNoOfGoods(int noOfGoods) {
		return new Person("name", noOfGoods, Occupation.UNEMPLOYED);
	}
}
