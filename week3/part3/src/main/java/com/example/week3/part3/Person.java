package com.example.week3.part3;

public class Person {
	private final String name;
	private final Integer numberOfBoughtGoods;
	private final Occupation occupation;

	public Person(String name, Integer numberOfBoughtGoods, Occupation occupation) {
		this.name = name;
		this.numberOfBoughtGoods = numberOfBoughtGoods;
		this.occupation = occupation;
	}

	public Integer getNumberOfBoughtGoods() {
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
				", numberOfGoods=" + numberOfBoughtGoods +
				", occupation=" + occupation +
				'}';
	}
}
