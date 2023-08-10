package com.example.week1.part4.done;

public interface DiscountApplier {
	Discount getDiscountRate(Person person);

	String getName();
}
