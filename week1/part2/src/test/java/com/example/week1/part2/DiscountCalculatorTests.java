package com.example.week1.part2;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DiscountCalculatorTests {

	DiscountCalculator d = new DiscountCalculator();

	List<DiscountApplier> a = new ArrayList<>();

	@Before
	public void setup() {
		 ReflectionUtils.injectDependency(DiscountCalculator.class, "discountAppliers", d, a);
	}

	@Test
	public void testCalculateTotalDiscountRate() {
		Person p = new Person("test", 1, Occupation.UNEMPLOYED);
		Discount dis = d.calculateTotalDiscountRate(p);
		Assert.assertEquals(0D, dis.getRate(), 0D);
	}
}
