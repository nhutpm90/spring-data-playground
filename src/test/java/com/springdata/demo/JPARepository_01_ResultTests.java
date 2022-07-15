package com.springdata.demo;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

import com.springdata.demo.entity.Employee;
import com.springdata.demo.repo.EmployeeRepo;
import com.springdata.demo.service.EmployeeService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@ComponentScan(basePackages = {"com.overview.springdata.h2demo.bootstrap",
							"com.springdata.demo.service"})
class JPARepository_01_ResultTests {

	@Autowired
	EmployeeRepo employeeRepo;
	
	@Autowired
	EmployeeService employeeService;
	
	@Test
	void contextLoads() {
	}

	private void breakLine(String header) {
		System.out.println("\n-----" + header);
	}
	
	private void printList(List<Employee> employees) {
		System.out.println("\n************* result:: size:: " + employees.size() + " *************");
		System.out.println(employees);
	}
	
	@Test
	void _01() {
		breakLine("------01 - employeesByFirstName");
		List<Employee> employeesByFirstName = this.employeeRepo.findByFirstName("Laurence");
		printList(employeesByFirstName);

		breakLine("------02 - employeesByLastName");
		List<Employee> employeesByLastName = this.employeeRepo.findByLastNameLike("%nn%");
		printList(employeesByLastName);
		
		breakLine("------03 - employeesByLastNameWithPaging");
		int page = 1;
		int pageSize = 3;
		PageRequest paging = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "firstName"));
		List<Employee> employeesByLastNameWithPaging = this.employeeRepo.findByLastNameLike("%n%", paging);
		printList(employeesByLastNameWithPaging);
		
		breakLine("------04 - allEmployees");
		Page<Employee> allEmployees = this.employeeRepo.findAll(paging);
		System.out.println(allEmployees);
		
		breakLine("------05 - findAllEmployeePartialData");
		List<Object[]> findAllEmployeePartialData = this.employeeRepo.findAllEmployeePartialData();
		System.out.println("\n************* result:: size:: " + findAllEmployeePartialData.size() + " *************");
		System.out.println(findAllEmployeePartialData);
		
		breakLine("------06 - findEmployeeByFirstNameAndGender using query");
		String firstName = "Jameson";
		String gender = "Male";
		List<Employee> findEmployeeByFirstNameAndGender = this.employeeRepo.findEmployeeByFirstNameAndGender(firstName,
				gender, PageRequest.of(0, 3));
		printList(findEmployeeByFirstNameAndGender);
		
		breakLine("------07 - findByIdIn");
		List<Long> employeeIds = Arrays.asList(1L, 2L, 3L);
		List<Employee> employeesByIds = this.employeeRepo.findByIdIn(employeeIds);
		printList(employeesByIds);
		
		breakLine("------08 - findEmployeeByGenderNativeQuery");
		List<Employee> employeesByGenderNativeQuery = this.employeeRepo.findEmployeeByGenderNativeQuery("Male", PageRequest.of(0, 3));
		printList(employeesByGenderNativeQuery);
		
		breakLine("------09 - deleteEmployee");
		this.employeeRepo.deleteEmployee("Rik");
		printList(this.employeeRepo.findAll());
	}
	
	@Test
	void _02() {
		String gender = "Male";
		String sortBy = "id";
		sortBy = null;
		Sort.Direction sortDirection = Sort.Direction.DESC;
		int page = 1;
		int size = 3;
		
		Page<Employee> filterEmployees = this.employeeService.filterEmployees(gender, sortBy, sortDirection, page, size);
		breakLine("------01 - filterEmployees");
		
		long totalElements = filterEmployees.getTotalElements();
		long totalPage = filterEmployees.getTotalPages();
		int pageNumber = filterEmployees.getNumber();
		int pageSize = filterEmployees.getSize();
		System.out.println("totalElements:: " + totalElements 
				+ " - totalPage:: " + totalPage 
				+ " - pageNumber:: " + pageNumber 
				+ " - pageSize:: " + pageSize);
		
		List<Employee> employees = filterEmployees.getContent();
		printList(employees);
	}
}
