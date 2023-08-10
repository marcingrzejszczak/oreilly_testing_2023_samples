package com.example.week2.part3.done;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

record PersonDetailsResponse(String name, String resourceId, PersonDetailsResponse.Status status) {

	@JsonCreator
	PersonDetailsResponse(@JsonProperty("name") String name,
			@JsonProperty("resourceId") String resourceId, @JsonProperty("status") Status status) {
		this.name = name;
		this.resourceId = resourceId;
		this.status = status;
	}


	enum Status {
		STORED, MANUAL_PROCESSING
	}

}
