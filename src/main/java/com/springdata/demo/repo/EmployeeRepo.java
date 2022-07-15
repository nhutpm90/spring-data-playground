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
	
	@Query("select e.firstName, e.lastName from Employee e")
	List<Object[]> findAllEmployeePartialData();
	
	@Query("select e from Employee e where e.firstName=:firstName and e.gender=:gender")
	List<Employee> findEmployeeByFirstNameAndGender(
			@Param("firstName") String firstName,
			@Param("gender") String gender,
			Pageable pageable);
	
	List<Employee> findByIdIn(List<Long> ids);
	
	@Query(value = "select * from Employee where gender=:gender", nativeQuery = true)
	List<Employee> findEmployeeByGenderNativeQuery(@Param("gender") String gender, Pageable pageable);
	
	@Modifying
	@Query("delete from Employee e where e.firstName=:firstName")
	void deleteEmployee(@Param("firstName") String firstName);
	
}
	
//	
//	Employee findByEmail(String email);
//	
//	@Query("from Employee")
//	List<Employee> findAllEmployee();
//	
//	@Query("select e from Employee e where e.gender=:gender")
//	List<Employee> findEmployeeByGender(@Param("gender") String gender, Pageable pageable);
//	
//	@Query(value = "select * from Employee where email=:email", nativeQuery = true)
//	Employee findEmployeeByEmailNativeQuery(@Param("email") String email);
//	
//	List<Employee> findByAddressCountry(String country);
//}
