package com.fdmgroup.employeeApi.exceptions;

public class NoEmployeeFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public NoEmployeeFoundException(long id) {
		super("Employee with ID " + id + " not found");
	}

	public NoEmployeeFoundException(String name) {
		super("Employee with ID " + name + " not found");
	}
}
