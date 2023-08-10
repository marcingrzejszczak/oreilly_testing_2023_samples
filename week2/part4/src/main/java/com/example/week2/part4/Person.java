package com.example.week2.part4;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {
	private final String name;
	private final int numberOfBoughtGoods;
	private final Occupation occupation;

	private double discountRate;

	@JsonCreator
	public Person(@JsonProperty("name") String name, @JsonProperty("numberOfBoughtGoods") int numberOfBoughtGoods, @JsonProperty("occupation") Occupation occupation) {
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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Person person = (Person) o;
		return numberOfBoughtGoods == person.numberOfBoughtGoods && Double.compare(person.discountRate, discountRate) == 0 && Objects.equals(name, person.name) && occupation == person.occupation;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, numberOfBoughtGoods, occupation, discountRate);
	}
}
