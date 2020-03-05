package com.example.hr.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.hr.entity.Employee;

@Service
public class DummyHrService implements HrService {

	@Override
	public List<Employee> getir(int page, int size) {
		Employee ben = new Employee();
		ben.setFullname("Ben Linus");
		ben.setIdentity("1111111111");
		ben.setIban("TR11111111111");
		return Arrays.asList(ben);
	}

}
