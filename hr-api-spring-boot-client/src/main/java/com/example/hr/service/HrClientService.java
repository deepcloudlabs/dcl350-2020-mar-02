package com.example.hr.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.hr.entity.Employee;

@Service
public class HrClientService {
	@Autowired
	private HrService hrSrv;
	@Autowired
	private StudyRetryStrategy myBinanceSrv;
	@Autowired
	@Qualifier("standard")
	private RestTemplate restTemplate;

	// @Scheduled(fixedRate = 1_000)
	public void callHrApi() {
		System.err.println(hrSrv.getClass());
		String msg = hrSrv.getir(0, 10).stream().map(Employee::getFullname).collect(Collectors.joining(",", "[", "]"));
		System.out.println(msg);
	}

	//@Scheduled(fixedRate = 1_000)
	public void callHrApiWithLoadBalancedRestTemplate() {
		String msg = restTemplate.getForEntity("http://localhost:5100/hr/api/v1/employees?page=0&size=10", String.class).getBody();
		System.out.println(msg);
	}

	// @Scheduled(fixedRate = 10_000)
	public void callBinanceApi() {
		System.out.println(myBinanceSrv.getTicker());
	}
}
