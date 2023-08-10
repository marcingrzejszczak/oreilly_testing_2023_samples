package com.example.week3.part4.done;

public class Person {

	private final long creationTimestamp;
	private final String name;
	private final int numberOfBoughtGoods;
	private final Occupation occupation;

	public Person(String name, int numberOfBoughtGoods, Occupation occupation) {
		this(System.currentTimeMillis(), name, numberOfBoughtGoods, occupation);
	}

	public Person(long creationTimestamp, String name, int numberOfBoughtGoods, Occupation occupation) {
		this.creationTimestamp = nonNegative(creationTimestamp);
		this.name = nonEmpty(name);
		this.numberOfBoughtGoods = nonNegative(numberOfBoughtGoods);
		this.occupation = nonNull(occupation);
	}

	private static String nonEmpty(String name) {
		if (name == null || name.length() < 2) {
			throw new IllegalArgumentException("Name can't be empty");
		}
		return name;
	}

	private static int nonNegative(int goods) {
		if (goods < 0) {
			throw new IllegalArgumentException("Number of goods can't be negative");
		}
		return goods;
	}

	private static long nonNegative(long timestamp) {
		if (timestamp < 0) {
			throw new IllegalArgumentException("Timestamp can't be negative");
		}
		return timestamp;
	}

	private static <T> T nonNull(T object) {
		if (object == null) {
			throw new IllegalArgumentException("Object can't be empty");
		}
		return object;
	}

	public long getCreationTimestamp() {
		return creationTimestamp;
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
