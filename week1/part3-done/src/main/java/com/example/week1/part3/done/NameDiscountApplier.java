package com.example.week1.part3.done;

public class NameDiscountApplier implements DiscountApplier {

	static final int THRESHOLD = 3;

	static final double DISCOUNT_RATE = 8D;

	private final MessageSender messageSender;

	public NameDiscountApplier(MessageSender messageSender) {
		this.messageSender = messageSender;
	}

	@Override
	public void applyDiscount(Person person) {
		System.out.println("Calculating name discount");
		person.setDiscountRate(person.getDiscountRate() + rate(person));
		messageSender.sendMessage(new Message(EventType.DISCOUNT_CALCULATED));
	}

	private double rate(Person person) {
		if (person.getName().length() < THRESHOLD) { // most likely a prank!
			return 0D;
		}
		return DISCOUNT_RATE;
	}
}
