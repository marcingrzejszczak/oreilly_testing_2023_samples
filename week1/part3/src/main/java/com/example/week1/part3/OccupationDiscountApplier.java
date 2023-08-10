package com.example.week1.part3;

public class OccupationDiscountApplier implements DiscountApplier {

	private final RateRepository rateRepository;
	private final MessageSender messageSender;

	public OccupationDiscountApplier(RateRepository rateRepository, MessageSender messageSender) {
		this.rateRepository = rateRepository;
		this.messageSender = messageSender;
	}

	@Override
	public void applyDiscount(Person person) {
		if (person.getOccupation() == Occupation.UNEMPLOYED) {
			System.out.println("Not calculating any discounts from the database for unemployed");
			return;
		}
		messageSender.sendMessage(new Message(EventType.DISCOUNT_CALCULATION_RECEIVED));
		System.out.println("Calculating occupation discount");
		try {
			person.setDiscountRate(person.getDiscountRate() + rateRepository.getDiscountRate(person.getOccupation()));
		} catch (Exception ex) {
			System.out.println("Exception occurred while trying to access the repository [" + ex + "]");
			messageSender.sendMessage(new Message(EventType.ERROR));
		}
	}
}
