package com.example.week3.part2.client;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.fasterxml.jackson.databind.ObjectMapper;

class PersonClient {

	private final HttpClient httpClient;

	private final String rootUrl;

	private final ObjectMapper objectMapper;

	PersonClient(HttpClient httpClient, String rootUrl, ObjectMapper objectMapper) {
		this.httpClient = httpClient;
		this.rootUrl = rootUrl;
		this.objectMapper = objectMapper;
	}

	DiscountResponse discount(Person person) {
		try {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(this.rootUrl + "/discount"))
					.timeout(Duration.ofMinutes(2))
					.header("Content-Type", "application/json")
					.POST(HttpRequest.BodyPublishers.ofByteArray(objectMapper.writeValueAsBytes(person)))
					.build();
			HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			return objectMapper.readValue(httpResponse.body(), DiscountResponse.class);
		}
		catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
