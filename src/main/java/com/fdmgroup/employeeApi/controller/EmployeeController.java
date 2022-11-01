package com.fdmgroup.employeeApi.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fdmgroup.employeeApi.exceptions.NoEmployeeFoundException;
import com.fdmgroup.employeeApi.model.Employee;
import com.fdmgroup.employeeApi.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/employees")
public class EmployeeController {

	EmployeeService employeeService;

	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@Operation(summary = "Returns a list of Employees in the database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operation successful", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "Operation unsuccessful", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@GetMapping
	public List<Employee> getAllEmployees() {
		return employeeService.retrieveAllEmployees();
	}

	@Operation(summary = "Returns a single Employee by ID from the database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operation successful", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "Operation unsuccessful", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@GetMapping("/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		try {
			Employee foundEmployee = employeeService.retrieveEmployeeById(id);
			return ResponseEntity.ok(foundEmployee);
		} catch (NoEmployeeFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@Operation(summary = "Returns a single Employee by first and last name from the database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operation successful", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "Operation unsuccessful", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@GetMapping("/{firstName}/{lastName}")
	public ResponseEntity<List<Employee>> getEmployeeByFirstAndLastName(@PathVariable String firstName,
			@PathVariable String lastName) {
		List<Employee> foundEmployees = employeeService.retrieveEmployeeByFirstAndLastName(firstName, lastName);
		return ResponseEntity.ok(foundEmployees);
	}

	@Operation(summary = "Creates a new Employee using parameters passed into the body")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Operation successful", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "Operation unsuccessful", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@PostMapping
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
		Employee returnedEmployee = employeeService.addEmployee(employee);
		URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(employee.getId()).toUri();
		return ResponseEntity.created(locationUri).body(returnedEmployee);
	}

	@Operation(summary = "Updates an Employee using parameters passed into the body")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operation successful", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "Operation unsuccessful", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@PutMapping
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
		Employee returnedEmployee = employeeService.updateEmployee(employee);
		return ResponseEntity.ok(returnedEmployee);
	}

	@Operation(summary = "Deletes an Employee using id passed into the path")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Operation successful", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }),
			@ApiResponse(responseCode = "404", description = "Operation unsuccessful", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE) }) })
	@DeleteMapping("/{id}")
	public ResponseEntity<Employee> deleteEmployeeById(@PathVariable long id) {
		System.out.println("inside");
		employeeService.removeEmployeeById(id);
		return ResponseEntity.ok().build();
	}
}
