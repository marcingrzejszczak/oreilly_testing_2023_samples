package com.example.week2.part3;

class PersonInManualProcessingException extends RuntimeException {

	PersonInManualProcessingException(PersonDetailsResponse personDetailsResponse) {
		super("Person [" + personDetailsResponse.name() + "] has a pending status for storing");
	}
}
