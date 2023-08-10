package com.example.week2.part5.done;

public class Discount {
	private final String name;

	private final Occupation occupation;

	private final double rate;

	public Discount(String name, Occupation occupation, double rate) {
		this.name = name;
		this.occupation = occupation;
		this.rate = rate;
	}

	public double getRate() {
		return rate;
	}

	public String getName() {
		return name;
	}

	public Occupation getOccupation() {
		return occupation;
	}
}
