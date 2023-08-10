package com.example.week2.part2.done;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class DiscountConfiguration {

	@Bean
	DiscountCalculator discountCalculator(List<DiscountApplier> appliers) {
		return new DiscountCalculator(appliers);
	}

	@Bean
	NameDiscountApplier nameDiscountApplier(@Value("${name.threshold:4}") int threshold, @Value("${name.discount:8.0}") double discountRate) {
		return new NameDiscountApplier(threshold, discountRate);
	}

	@Bean
	NoOfBoughtGoodsDiscountApplier noOfBoughtGoodsDiscountApplier(@Value("${goods.threshold:6}") int threshold, @Value("${goods.discount:5.0}") double discountRate) {
		return new NoOfBoughtGoodsDiscountApplier(threshold, discountRate);
	}

	@Bean
	FixedRateRepository fixedRateRepository() {
		return new FixedRateRepository();
	}

	@Bean
	OccupationDiscountApplier occupationDiscountApplier(RateRepository rateRepository) {
		return new OccupationDiscountApplier(rateRepository);
	}
}
