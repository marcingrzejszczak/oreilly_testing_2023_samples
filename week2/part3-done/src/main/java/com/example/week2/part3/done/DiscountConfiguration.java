package com.example.week2.part3.done;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration(proxyBeanMethods = false)
class DiscountConfiguration {

	@Bean
	DiscountCalculator discountCalculator(List<DiscountApplier> appliers) {
		return new DiscountCalculator(appliers);
	}

	@Bean
	PersonDetailsDiscountApplier personDetailsDiscountApplier(PersonClient personClient) {
		return new PersonDetailsDiscountApplier(personClient);
	}

	@Configuration(proxyBeanMethods = false)
	static class ClientConfiguration {
		@Bean
		PersonClient personClient(@Qualifier("personRestTemplate") RestTemplate restTemplate, @Value("${person.url}") String url) {
			return new PersonClient(restTemplate, url);
		}

		@Bean
		RestTemplate personRestTemplate(@Value("${person.read-timeout:500}") int readTimeout, @Value("${person.connect-timeout:500}") int connectTimeout) {
			return new RestTemplate(simpleClientHttpRequestFactory(readTimeout, connectTimeout));
		}

		private SimpleClientHttpRequestFactory simpleClientHttpRequestFactory(int readTimeout, int connectTimeout) {
			SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
			factory.setConnectTimeout(readTimeout);
			factory.setReadTimeout(connectTimeout);
			return factory;
		}
	}

}
