package com.springdata.demo.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.springdata.demo.entity.Employee;
import com.springdata.demo.repo.EmployeeRepo;

@Service
public class EmployeeService {

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private EmployeeRepo employeeRepo;

	public Page<Employee> employees(String sortBy, Sort.Direction sortDirection, int page, int size) {
		Sort sort = Sort.by(sortDirection, sortBy);
		PageRequest pageRequest = PageRequest.of(page, size, sort);
		return this.employeeRepo.findAll(pageRequest);
	}
	
	public Page<Employee> filterEmployees(String gender, String sortBy, 
			Sort.Direction sortDirection, int page, int size) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
		Root<Employee> root = query.from(Employee.class);
		
		List<Predicate> conditions = new ArrayList<Predicate>();
		if (StringUtils.hasLength(gender)) {
			conditions.add(cb.equal(root.get("gender"), gender));
		}
		
		Predicate predicate = cb.and(conditions.toArray(new Predicate[0]));
		
		query.where(predicate);
		
		if(sortBy != null && sortDirection != null) {
			if(sortDirection.equals(Sort.Direction.ASC)) {
				query.orderBy(cb.asc(root.get(sortBy)));
			} else {
				query.orderBy(cb.desc(root.get(sortBy)));
			}
		}
		
		List<Employee> employees = em.createQuery(query)
				.setFirstResult(page * size).setMaxResults(size)
				.getResultList();
		
		Sort sort = Sort.unsorted();
		if(sortBy != null && sortDirection != null) {
			sort = Sort.by(sortDirection, sortBy);
		}
		PageRequest pageRequest = PageRequest.of(page, size, sort);
				
		Long count = this.countEmployee(predicate);
		Page<Employee> ret = new PageImpl<>(employees, pageRequest, count);
		
		return ret;
	}
	
	private Long countEmployee(Predicate conditions) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> query = cb.createQuery(Long.class);
		Root<Employee> root = query.from(Employee.class);
		return em.createQuery(query.select(cb.count(root)).where(conditions)).getSingleResult();
	}
	
}
