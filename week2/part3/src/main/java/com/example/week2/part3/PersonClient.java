package com.example.week2.part3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class PersonClient {

	private static final Logger log = LoggerFactory.getLogger(PersonClient.class);

	private final RestTemplate restTemplate;

	private final String rootUrl;

	PersonClient(RestTemplate restTemplate, String rootUrl) {
		this.restTemplate = restTemplate;
		this.rootUrl = rootUrl;
	}

	PersonDetailsResponse updatePersonDetails(Person person) {
		ResponseEntity<PersonDetailsResponse> entity;
		try {
			entity = restTemplate.exchange(
					RequestEntity.post(this.rootUrl + "/person/" + person.getName())
					.contentType(MediaType.APPLICATION_JSON)
					.body(person), PersonDetailsResponse.class);
		}
		catch (Exception ex) {
			throw new PersonNotStoredException(person, ex);
		}
		PersonDetailsResponse personDetailsResponse = entity.getBody();
		if (personDetailsResponse.status() == PersonDetailsResponse.Status.MANUAL_PROCESSING) {
			throw new PersonInManualProcessingException(personDetailsResponse);
		}
		return personDetailsResponse;
	}

	double getDiscount(String resourceId) {
		try {
			return restTemplate.getForObject(this.rootUrl + "/person/" + resourceId + "/discount", PersonDiscountResponse.class).discountRate();
		} catch (Exception ex) {
			log.warn("Exception occurred while trying to fetch a discount for person with id [" + resourceId + "]. Will not apply any discounts", ex);
			return 0;
		}
	}
}
