package com.example.week1.part3;

public class NoOfBoughtGoodsDiscountApplier implements DiscountApplier {

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
		if (person.getNumberOfBoughtGoods() < 5) {
			return 0D;
		}
		return 5D;
	}
}
