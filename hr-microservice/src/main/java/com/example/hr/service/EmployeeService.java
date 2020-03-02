package com.example.hr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.hr.entity.Employee;
import com.example.hr.repository.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository empRepo;
	public Employee findByIdentity(String identity) {
		// TODO Auto-generated method stub
		return null;
	}

}
