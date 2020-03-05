package com.example.hr.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.hr.entity.Employee;

@FeignClient(name = "hr", 
             fallback = DummyHrService.class)
public interface HrService {
	@GetMapping("/hr/api/v1/employees")
	public List<Employee> getir(
			@RequestParam int page,
			@RequestParam int size);
}
