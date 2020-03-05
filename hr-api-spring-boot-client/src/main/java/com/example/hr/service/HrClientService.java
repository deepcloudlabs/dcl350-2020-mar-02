package com.example.hr.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HrClientService {
	private static final String URL = "http://%s:%d/hr/api/v1/employees?page=0&size=10";
	@Autowired 
	private DiscoveryClient discoveryClient;
	private List<ServiceInstance> instances;
	private AtomicInteger counter = 
			new AtomicInteger();
	@PostConstruct
	public void init() {
		// spring.application.name
		instances = discoveryClient.getInstances("hr");		  
	    instances.forEach( instance -> {
	    	System.err.println(instance.getHost()
	    			+ ":" + instance.getPort());
	    });
	}
	
	@Scheduled(fixedRate = 1_000)
	public void callHrApi() {
		RestTemplate rt = new RestTemplate();
		int index = counter.getAndIncrement() % instances.size();
		ServiceInstance instance = 
				      instances.get(index);
		String requestUrl = String.format(URL, 
			instance.getHost(),instance.getPort());
		String employees = rt.getForEntity(
				requestUrl, String.class).getBody();
		System.out.println(employees);
	}
}
