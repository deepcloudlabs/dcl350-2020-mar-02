package com.example.hr.service;

import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

public class HrApiClientWithCircuitBreaker {
	@CircuitBreaker(name = "hrService", fallbackMethod = "getFallbackEmployees")
	public String getEmployees(int page, int size) {
		String url = "http://localhost:5100/hr/api/v1/employees?page=0&size=25";
		RestTemplate rt = new RestTemplate();
		return rt.getForEntity(url, String.class).getBody();
	}

	public String getFallbackEmployees(int page, int size, Exception e) {
		System.out.println("Falling back : page=" + page + ", size=" + size);
		return "[]";
	}
}
