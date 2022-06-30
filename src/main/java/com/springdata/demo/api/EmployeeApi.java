package com.springdata.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springdata.demo.entity.Employee;
import com.springdata.demo.service.EmployeeService;

@RestController
public class EmployeeApi {

	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping("/employees")
	public List<Employee> employees() throws Exception {
		return this.employeeService.employees();
	}
}
