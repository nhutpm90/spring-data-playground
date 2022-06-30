package com.springdata.demo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springdata.demo.entity.Employee;
import com.springdata.demo.service.EmployeeService;

@RestController
public class EmployeeApi {

	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/employees")
	public Page<Employee> employees(
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "ASC") Sort.Direction sortDirection,
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int size) throws Exception {
		return this.employeeService.employees(sortBy, sortDirection, page, size);
	}
	
	@GetMapping("/employees/filter")
	public Page<Employee> filterEmployees(
			@RequestParam(required=false) String gender,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(defaultValue = "ASC") Sort.Direction sortDirection,
			@RequestParam(defaultValue = "0") int page, 
			@RequestParam(defaultValue = "10") int size) throws Exception {
		return this.employeeService.filterEmployees(gender, sortBy, sortDirection, page, size);
	}
}
