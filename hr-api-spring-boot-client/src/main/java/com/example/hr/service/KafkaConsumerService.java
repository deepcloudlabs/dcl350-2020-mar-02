package com.example.hr.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.hr.entity.Employee;

// bin/kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic employees
@Service
public class KafkaConsumerService {
	@KafkaListener(topics = "employees")
	public void listen(Employee employee) {
		System.out.println("[KafkaConsumerService] Received from kafka topic: " + employee);
	}
}
