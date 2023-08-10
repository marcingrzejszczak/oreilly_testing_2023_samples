package com.example.week3.part4.done;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;

import static org.assertj.core.api.BDDAssertions.then;


class NoOfBoughtGoodsDiscountApplierTests {

	@Property
	void should_return_discount_for_number_of_goods_above_threshold(@ForAll @IntRange(min = NoOfBoughtGoodsDiscountApplier.THRESHOLD + 1) int numberOfGoods) {
		then(new NoOfBoughtGoodsDiscountApplier().getDiscountRate(new Person( "foo", numberOfGoods, Occupation.UNEMPLOYED))).isEqualTo(NoOfBoughtGoodsDiscountApplier.DISCOUNT_RATE);
	}

	@Property
	void should_return_no_discount_for_number_of_goods_below_threshold(@ForAll @IntRange(max = NoOfBoughtGoodsDiscountApplier.THRESHOLD) int numberOfGoods) {
		then(new NoOfBoughtGoodsDiscountApplier().getDiscountRate(new Person( "foo", numberOfGoods, Occupation.UNEMPLOYED))).isZero();
	}

}
