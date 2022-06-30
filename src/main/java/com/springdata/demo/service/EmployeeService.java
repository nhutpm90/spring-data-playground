package com.springdata.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springdata.demo.entity.Employee;
import com.springdata.demo.repo.EmployeeRepo;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepo employeeRepo;
	
	public List<Employee> employees() {
		return this.employeeRepo.findAll();
	}
}
