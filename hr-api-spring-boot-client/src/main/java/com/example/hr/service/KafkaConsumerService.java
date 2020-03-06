package com.example.hr.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.hr.entity.Employee;

@Service
public class KafkaConsumerService {
	@KafkaListener(topics = "employees")
	public void listen(Employee employee) {
		System.out.println("Received from kafka topic: "
	            + employee );
	}
}
