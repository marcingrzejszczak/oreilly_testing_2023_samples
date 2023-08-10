package com.example.week1.part3.done;

public class NoOfBoughtGoodsDiscountApplier implements DiscountApplier {
	// This should be configurable
	static int THRESHOLD = 5;

	static double DISCOUNT_RATE = 5D;

	private final MessageSender messageSender;

	public NoOfBoughtGoodsDiscountApplier(MessageSender messageSender) {
		this.messageSender = messageSender;
	}

	@Override
	public void applyDiscount(Person person) {
		messageSender.sendMessage(new Message(EventType.DISCOUNT_CALCULATION_RECEIVED));
		System.out.println("Calculating number of goods discount");
		person.setDiscountRate(person.getDiscountRate() + rate(person));
		// THIS EVENT MUST BE CALLED AFTER RECEIVED!!
		messageSender.sendMessage(new Message(EventType.DISCOUNT_CALCULATED));
	}

	private static double rate(Person person) {
		if (person.getNumberOfBoughtGoods() < THRESHOLD) {
			return 0D;
		}
		return DISCOUNT_RATE;
	}
}
