package com.example.hr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableEurekaClient
@EnableDiscoveryClient
@EnableRetry
@EnableFeignClients(basePackages = "com.example.hr.service")
public class HrApiSpringBootClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrApiSpringBootClientApplication.class, args);
	}

}
