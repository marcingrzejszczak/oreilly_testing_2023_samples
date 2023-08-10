package com.example.week1.part4;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Person {
	private final String name;
	private final int numberOfBoughtGoods;
	private final Occupation occupation;

	private final List<Discount> discounts = new CopyOnWriteArrayList<>();

	public Person(String name, int numberOfBoughtGoods, Occupation occupation) {
		this.name = name;
		this.numberOfBoughtGoods = numberOfBoughtGoods;
		this.occupation = occupation;
	}

	public void addDiscount(Discount discount) {
		this.discounts.add(discount);
	}

	public Discount getTotalDiscount() {
		return new Discount("total", this.discounts.stream()
				.map(Discount::getRate)
				.reduce(0D, Double::sum));
	}

	public List<Discount> getDiscounts() {
		return discounts;
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

	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				", numberOfBoughtGoods=" + numberOfBoughtGoods +
				", occupation=" + occupation +
				", discounts=" + discounts +
				'}';
	}
}
