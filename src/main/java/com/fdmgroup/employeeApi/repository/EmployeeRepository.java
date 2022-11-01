package com.fdmgroup.employeeApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import com.fdmgroup.employeeApi.model.Employee;

@Component
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	@Query("select e from Employee e where upper(e.firstName) = upper(:firstName) and upper(e.lastName) = upper(:lastName)")
	List<Employee> findByFirstAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
