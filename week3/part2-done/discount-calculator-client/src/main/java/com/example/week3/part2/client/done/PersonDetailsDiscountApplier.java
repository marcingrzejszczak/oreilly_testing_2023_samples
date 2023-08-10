package com.example.week3.part2.client.done;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PersonDetailsDiscountApplier implements DiscountApplier {

	private static final Logger log = LoggerFactory.getLogger(PersonDetailsDiscountApplier.class);

	private final PersonClient personClient;

	PersonDetailsDiscountApplier(PersonClient personClient) {
		this.personClient = personClient;
	}

	@Override
	public void applyDiscount(Person person) {
		log.info("Calling person details service over HTTP");
		DiscountResponse discountResponse = personClient.discount(person);
		double discountRate = discountResponse.discountRate();
		log.debug("For person [{}] found discount [{}]", person, discountRate);
		person.setDiscountRate(discountRate);
	}
}
