package com.example.hr.service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hr.dto.UpdateEmployeeRequest;
import com.example.hr.entity.Department;
import com.example.hr.entity.Employee;
import com.example.hr.repository.EmployeeRepository;

@Service
public class EmployeeService {
	private static final String[] UPDATABLE_FIELDS = { "salary", "iban", "photo", "department", "fulltime" };
	@Autowired
	private EmployeeRepository empRepo;

	public Employee findByIdentity(String identity) {
		return empRepo.findById(identity).orElseThrow(() -> new IllegalArgumentException("Cannot find employee"));
	}

	public List<Employee> findEmployees(int page, int size) {
		return empRepo.findAll(PageRequest.of(page, size)).getContent();
	}

	@Transactional
	public Employee addEmployee(Employee employee) {
		String identity = employee.getIdentity();
		if (empRepo.existsById(identity)) {
			throw new IllegalArgumentException("Employee already exists!");
		}
		return empRepo.save(employee);
	}

	@Transactional
	public Employee updateEmployee(UpdateEmployeeRequest employee, String identity) {
		Optional<Employee> emp = empRepo.findById(identity);
		if (!emp.isPresent())
			throw new IllegalArgumentException("Cannot find employee to update");
		Employee managed = emp.get();
		managed.setIban(employee.getIban());
		managed.setSalary(employee.getSalary());
		managed.setFulltime(employee.isFulltime());
		managed.setPhoto(employee.getPhoto());
		managed.setDepartment(employee.getDepartment());
		return managed;
	}

	@Transactional
	public Employee patchEmployee(Map<String, Object> employee, String identity) {
		Optional<Employee> emp = empRepo.findById(identity);
		if (!emp.isPresent())
			throw new IllegalArgumentException("Cannot find employee to patch");
		Employee managed = emp.get();
		if (employee.containsKey("department"))
			employee.put("department", Department.valueOf(employee.get("department").toString()));
		Arrays.stream(UPDATABLE_FIELDS).forEach(field -> {
			if (employee.containsKey(field)) {
				Object value = employee.get(field);
				try {
					Field managedField = Employee.class.getDeclaredField(field);
					managedField.setAccessible(true);
					managedField.set(managed, value);
					managedField.setAccessible(false);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		});
		return managed;
	}

}
