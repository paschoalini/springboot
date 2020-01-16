package com.paschoalini.springboot.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.paschoalini.springboot.models.Student;

public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
	List<Student> findByNameIgnoreCaseContaining(String name);
}
