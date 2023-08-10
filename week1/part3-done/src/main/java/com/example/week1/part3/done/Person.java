package com.example.week1.part3.done;

public class Person {
	private final String name;
	private final int numberOfBoughtGoods;
	private final Occupation occupation;

	private double discountRate;

	public Person(String name, int numberOfBoughtGoods, Occupation occupation) {
		this.name = name;
		this.numberOfBoughtGoods = numberOfBoughtGoods;
		this.occupation = occupation;
	}

	public int getNumberOfBoughtGoods() {
		return numberOfBoughtGoods;
	}

	public String getName() {
		return name;
	}

	public Occupation getOccupation() {
		return occupation;
	}

	public double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(double discountRate) {
		this.discountRate = discountRate;
	}

	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				", numberOfBoughtGoods=" + numberOfBoughtGoods +
				", occupation=" + occupation +
				", discountRate=" + discountRate +
				'}';
	}
}
