package com.example.hr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

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
	public Employee findEmployeeByIdentity(
			@PathVariable
			String identity) {
		return empSrv.findByIdentity(identity);
	}
}



