package com.example.hr.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.example.hr.dto.UpdateEmployeeRequest;
import com.example.hr.entity.Employee;
import com.example.hr.service.EmployeeService;

// localhost:5100/hr/api/v1/employees
@RestController
@RequestScope
@RequestMapping("employees")
@CrossOrigin
public class EmployeeRestController {
	@Autowired
	private EmployeeService empSrv;

	// /hr/api/v1/employees/12345678910
	@GetMapping("{identity}")
	public Employee findEmployeeByIdentity(@PathVariable String identity) {
		return empSrv.findByIdentity(identity);
	}

	// /hr/api/v1/employees?page=10&size=25
	@GetMapping
	public List<Employee> findEmployees(@RequestParam int page, @RequestParam int size) {
		return empSrv.findEmployees(page, size);
	}

	@PostMapping
	public Employee createEmployee(@RequestBody Employee employee) {
		return empSrv.addEmployee(employee);
	}

	@PutMapping("{identity}")
	public Employee updateEmployee(@RequestBody UpdateEmployeeRequest employee, @PathVariable String identity) {
		return empSrv.updateEmployee(employee, identity);
	}

	@PatchMapping("{identity}")
	public Employee patchEmployee(@RequestBody Map<String, Object> employee, @PathVariable String identity) {
		return empSrv.patchEmployee(employee, identity);
	}
}
