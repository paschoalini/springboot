package com.paschoalini.springboot.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.paschoalini.springboot.models.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {
	List<Student> findByNameIgnoreCaseContaining(String name);
}
