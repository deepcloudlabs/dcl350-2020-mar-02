package com.example.hr.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.LoadBalancerBuilder;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.Server;

@Service
public class HrClientService {
	private static final String URL = "//hr/hr/api/v1/employees?page=0&size=10";
//	@Autowired 
//	private DiscoveryClient discoveryClient;
//	private List<ServiceInstance> instances;
//	private BaseLoadBalancer loadBalancer;
	/*
	@PostConstruct
	public void init() {
		instances = discoveryClient.getInstances("hr");
		List<Server> servers = instances.stream()
				.map( instance -> 
				new Server(instance.getHost(), 
						    instance.getPort()))
				.collect(Collectors.toList()); 
		IRule roundRobinRule =new RoundRobinRule();
		loadBalancer = LoadBalancerBuilder.newBuilder()
			.withRule(roundRobinRule)
		.buildFixedServerListLoadBalancer(servers);
	}
	*/
	@Autowired
	private RestTemplate restTemplate;
	
	@Scheduled(fixedRate = 1_000)
	public void callHrApi() {
		/*
		RestTemplate rt = new RestTemplate();
		Server server = loadBalancer.chooseServer();
		String requestUrl = String.format(URL, 
				server.getHost(),server.getPort());
		System.out.println(requestUrl);
		*/		
		String employees = restTemplate.getForEntity(
				URL, String.class).getBody();
	}
}
