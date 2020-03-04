package com.example.hr.controller;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hr.dto.UpdateEmployeeRequest;
import com.example.hr.entity.Employee;
import com.example.hr.service.EmployeeService;
import com.example.validation.TcKimlikNo;

// localhost:5100/hr/api/v1/employees
@RestController
@RequestMapping("employees")
@CrossOrigin
@Validated
public class EmployeeRestController {
	@Autowired
	private EmployeeService empSrv;

	// /hr/api/v1/employees/12345678910
	@GetMapping("{identity}")
	public Employee findEmployeeByIdentity(@PathVariable @TcKimlikNo String identity) {
		return empSrv.findByIdentity(identity);
	}

	// /hr/api/v1/employees?page=10&size=25
	@GetMapping
	public List<Employee> findEmployees(@RequestParam @Min(0) int page, @RequestParam @Max(25) int size) {
		return empSrv.findEmployees(page, size);
	}

	@PostMapping
	public Employee createEmployee(@RequestBody @Validated Employee employee) {
		return empSrv.addEmployee(employee);
	}

	@PutMapping("{identity}")
	public Employee updateEmployee(@RequestBody @Validated UpdateEmployeeRequest employee,
			@PathVariable @TcKimlikNo String identity) {
		return empSrv.updateEmployee(employee, identity);
	}

	@PatchMapping("{identity}")
	public Employee patchEmployee(@RequestBody Map<String, Object> employee, @PathVariable String identity) {
		return empSrv.patchEmployee(employee, identity);
	}

	@DeleteMapping("{identity}")
	public Employee deleteEmployeeByIdentity(@PathVariable @TcKimlikNo String identity) {
		return empSrv.removeByIdentity(identity);
	}
}
