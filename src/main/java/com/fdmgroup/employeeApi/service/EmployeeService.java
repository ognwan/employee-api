package com.fdmgroup.employeeApi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fdmgroup.employeeApi.exceptions.NoEmployeeFoundException;
import com.fdmgroup.employeeApi.model.Employee;
import com.fdmgroup.employeeApi.repository.EmployeeRepository;

@Service
public class EmployeeService {

	private EmployeeRepository employeeRepository;

	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository) {
		super();
		this.employeeRepository = employeeRepository;
	}

	public List<Employee> retrieveAllEmployees() {
		return employeeRepository.findAll();
	}

	public Employee retrieveEmployeeById(Long id) throws NoEmployeeFoundException {
		return employeeRepository.findById(id).orElseThrow(() -> new NoEmployeeFoundException(id));
	}

	public List<Employee> retrieveEmployeeByFirstAndLastName(String fName, String lName) {

		return employeeRepository.findByFirstAndLastName(fName, lName);
	}

	public Employee addEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	public ResponseEntity<Employee> removeEmployeeById(long id) {
		employeeRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

	public Employee updateEmployee(Employee employee) {
		Employee returnedEmployee = employeeRepository.findById(employee.getId()).get();
		returnedEmployee.setFirstName(employee.getFirstName());
		returnedEmployee.setLastName(employee.getLastName());
		returnedEmployee.setSalary(employee.getSalary());
		return employeeRepository.save(returnedEmployee);
	}

}
