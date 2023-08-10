package com.example.week1.part3;

public class NameDiscountApplier implements DiscountApplier {

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
		if (person.getName().length() < 3) { // most likely a prank!
			return 0D;
		}
		return 8D;
	}
}
