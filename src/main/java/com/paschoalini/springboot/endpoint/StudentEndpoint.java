package com.paschoalini.springboot.endpoint;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paschoalini.springboot.error.ResourceNotFoundException;
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
	public ResponseEntity<?> listAll(Pageable pageable) {
		return new ResponseEntity<>(studentDAO.findAll(pageable), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userDetails) {
		System.out.println("AuthoUsername........ " + userDetails.getUsername());
		verifyIfStudentExists(id);		
		return new ResponseEntity<>(studentDAO.findById(id).get(), HttpStatus.OK);
	}
	
	@GetMapping("/findByName/{name}")
	public ResponseEntity<?> findStudentByName(@PathVariable String name) {
		return new ResponseEntity<>(studentDAO.findByNameIgnoreCaseContaining(name), HttpStatus.OK);
	}
	
	@PostMapping
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<?> save(@Valid @RequestBody Student student) {
		return new ResponseEntity<>(studentDAO.save(student), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		verifyIfStudentExists(id);
		studentDAO.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<?> update(@RequestBody Student student) {
		verifyIfStudentExists(student.getId());
		studentDAO.save(student);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private void verifyIfStudentExists(Long id) {		
		if(!studentDAO.existsById(id)) {
			throw new ResourceNotFoundException("Student not found. ID: " + id);
		}
	}
}
