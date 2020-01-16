package com.paschoalini.springboot.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paschoalini.springboot.error.CustomErrorType;
import com.paschoalini.springboot.models.Student;
import com.paschoalini.springboot.repository.StudentRepository;

@RestController
@RequestMapping("/students")
public class StudentEndpoint {
	private final StudentRepository studentDAO;
	
	@Autowired
	public StudentEndpoint(StudentRepository studentDAO) {
		this.studentDAO = studentDAO;
	}
	
	@GetMapping
	public ResponseEntity<?> listAll() {
		return new ResponseEntity<>(studentDAO.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
		Student student = studentDAO.findById(id).get();
		
		if(student == null) {
			return new ResponseEntity<>(new CustomErrorType("Student not found. ID: " + id), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(student, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> save(@RequestBody Student student) {
		return new ResponseEntity<>(studentDAO.save(student), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		studentDAO.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<?> update(@RequestBody Student student) {
		studentDAO.save(student);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
