package com.example.week2.part3.done;

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
		PersonDetailsResponse personDetails = personClient.updatePersonDetails(person);
		double discount = personClient.getDiscount(personDetails.resourceId());
		log.debug("For person [{}] found discount [{}]", person, discount);
		person.setDiscountRate(person.getDiscountRate() + discount);
	}
}
