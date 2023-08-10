package com.example.week3.part1.done.repository;

import com.example.week3.part1.done.model.Occupation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DbRateRepository implements RateRepository {

	private static final Logger log = LoggerFactory.getLogger(DbRateRepository.class);

	@Override
	public double getDiscountRate(Occupation occupation) {
		log.info("Got occupation [{}]", occupation);
		return OccupationUtils.getRateForOccupation(occupation);
	}
}
