package com.example.week3.part2.client.done;

import java.net.http.HttpClient;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Week2Part3DiscountClient {

	// name noOfBoughtGoods Occupation serverUrl
	// foo 15 EMPLOYED
	// bar 5 UNEMPLOYED
	public static void main(String[] args) {
		if (args.length != 4) {
			throw new IllegalArgumentException("Wrong number of arguments " + Arrays.toString(args) + ". There must be exactly 4 arguments");
		}
		String name = args[0];
		int noOfGoods = Integer.parseInt(args[1]);
		Occupation occupation = Occupation.valueOf(args[2]);
		String rootUrl = args[3];
		Week2Part3DiscountClient client = new Week2Part3DiscountClient(name, noOfGoods, occupation, HttpClient.newBuilder().build(), rootUrl, new ObjectMapper());
		client.discount();
	}


	private final String name;

	private final int noOfGoods;

	private final Occupation occupation;

	private final HttpClient httpClient;

	private final String rootUrl;

	private final ObjectMapper objectMapper;

	Week2Part3DiscountClient(String name, int noOfGoods, Occupation occupation, HttpClient httpClient, String rootUrl, ObjectMapper objectMapper) {
		this.name = name;
		this.noOfGoods = noOfGoods;
		this.occupation = occupation;
		this.httpClient = httpClient;
		this.rootUrl = rootUrl;
		this.objectMapper = objectMapper;
	}

	void discount() {
		new PersonDetailsDiscountApplier(new PersonClient(this.httpClient, this.rootUrl, this.objectMapper)).applyDiscount(new Person(this.name, this.noOfGoods, this.occupation));
	}

}
