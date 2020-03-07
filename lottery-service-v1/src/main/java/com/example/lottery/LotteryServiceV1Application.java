package com.example.lottery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

// set PATH=c:\stage\opt\curl-7.45.0\bin;%PATH%
// curl http://localhost:7300/api/v1/signin -X POST  -d "{\"username\": \"user\", \"password\": \"secret\"}" -H "Content-Type: application/json"
// curl http://localhost:7300/api/v1/numbers?n=4  -H "Authorization: Bearer <jwt>"
@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
public class LotteryServiceV1Application {

	public static void main(String[] args) {
		SpringApplication.run(LotteryServiceV1Application.class, args);
	}

}
