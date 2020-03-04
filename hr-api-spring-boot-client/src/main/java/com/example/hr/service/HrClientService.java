package com.example.hr.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HrClientService {
	private static final String URL = 
		     "http://localhost:5100/hr/api/v1"
			+ "/employees?page=0&size=10";
	@Scheduled(fixedRate = 10_000)
	public void callHrApi() {
		RestTemplate rt = new RestTemplate();
		String employees = 
				rt.getForEntity(URL, String.class)
				  .getBody();
		System.out.println(employees);
	}
}
