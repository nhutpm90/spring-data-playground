package com.springdata.demo.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springdata.demo.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {

	List<Employee> findByFirstName(String firstName);
	
	List<Employee> findByLastNameLike(String lastName);
	
	List<Employee> findByLastNameLike(String lastName, Pageable pageable);
	
	Employee findByEmail(String email);
	
	List<Employee> findByIdIn(List<Long> ids);
	
	@Query("from Employee")
	List<Employee> findAllEmployee();
	
	@Query("select e.firstName, e.lastName from Employee e")
	List<Object[]> findAllEmployeePartialData();
	
	@Query("select e from Employee e where e.firstName=:firstName")
	List<Employee> findEmployee(@Param("firstName") String firstName);
	
	@Query("select e from Employee e where e.gender=:gender")
	List<Employee> findEmployeeByGender(@Param("gender") String gender, Pageable pageable);
	
	@Query(value = "select * from Employee where email=:email", nativeQuery = true)
	Employee findEmployeeByEmail(@Param("email") String email);
	
	@Modifying
	@Query("delete from Employee e where e.firstName=:firstName")
	void deleteEmployee(@Param("firstName") String firstName);
	
	List<Employee> findByAddressCountry(String country);
}
