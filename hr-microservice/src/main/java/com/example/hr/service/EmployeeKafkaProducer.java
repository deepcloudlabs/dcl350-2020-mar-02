package com.example.hr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.hr.entity.Employee;

@Service
public class EmployeeKafkaProducer {
	@Autowired
	private KafkaTemplate<String,Employee> kafkaTemplate;
	
	@EventListener
	public void listenNewEmployees(Employee emp) {
		kafkaTemplate.send("employees", 
				emp.getIdentity(), emp);
	}
}
