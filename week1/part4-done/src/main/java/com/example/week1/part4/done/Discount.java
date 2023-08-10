package com.example.week1.part4.done;

public class Discount {

	private final String applierName;

	private final double rate;

	public Discount(String applierName, double rate) {
		this.applierName = applierName;
		this.rate = rate;
	}

	public double getRate() {
		return rate;
	}

	public String getApplierName() {
		return applierName;
	}

	@Override
	public String toString() {
		return "Discount{" +
				"applierName='" + applierName + '\'' +
				", rate=" + rate +
				'}';
	}
}
