package com.example.hr.service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.hr.dto.UpdateEmployeeRequest;
import com.example.hr.entity.Department;
import com.example.hr.entity.Employee;
import com.example.hr.repository.EmployeeRepository;
import com.example.hr.service.exception.DuplicateEmployeeException;
import com.example.hr.service.exception.EmployeeNotFoundException;

@Service
public class EmployeeService {
	private static final String[] UPDATABLE_FIELDS = { "salary", "iban", "photo", "department", "fulltime" };
	@Autowired
	private ApplicationEventPublisher publisher;

	private EmployeeRepository empRepo;
	@Value("${server.port}")
	private int port;

	@Autowired
	public void setEmpRepo(EmployeeRepository empRepo) {
		this.empRepo = empRepo;
	}

	public Employee findByIdentity(String identity) {
		return empRepo.findById(identity)
				.orElseThrow(() -> new EmployeeNotFoundException("Cannot find employee to retrieve", "140",
						"d673bc92-9f6f-44d7-8ef6-716ae1b32861", identity));
	}

	public List<Employee> findEmployees(int page, int size) {
		System.err.println("Serving at port " + port);
		return empRepo.findAll(PageRequest.of(page, size)).getContent();
	}

	@Transactional(rollbackFor = IllegalArgumentException.class)
	public Employee addEmployee(Employee employee) {
		String identity = employee.getIdentity();
		if (empRepo.existsById(identity)) {
			throw new DuplicateEmployeeException("Employee already exists!", "100", // ==> i18n
					"1c94dd33-102c-4aea-b627-900a07215718", // ==> debug
					identity);
		}
		Employee saved = empRepo.save(employee);
		empRepo.flush();
		publisher.publishEvent(employee);
		return saved;
	}

	@Transactional
	public Employee updateEmployee(UpdateEmployeeRequest employee, String identity) {
		Optional<Employee> emp = empRepo.findById(identity);
		if (!emp.isPresent())
			throw new EmployeeNotFoundException("Cannot find employee to update", "110",
					"ad546e2b-cfd8-4ba2-ace9-9af82d14b9d9", identity);
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
			throw new EmployeeNotFoundException("Cannot find employee to patch", "120",
					"220f5b38-38d2-42dd-8066-95f6390f50a5", identity);
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

	@Transactional
	public Employee removeByIdentity(String identity) {
		Optional<Employee> employee = empRepo.findById(identity);
		if (!employee.isPresent())
			throw new EmployeeNotFoundException("Cannot find employee to delete", "130",
					"accc58f8-4b11-4772-ba07-6829da831468", identity);
		empRepo.deleteById(identity);
		return employee.get();
	}

}
