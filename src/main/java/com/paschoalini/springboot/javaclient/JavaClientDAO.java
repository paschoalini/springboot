package com.paschoalini.springboot.javaclient;

import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.paschoalini.springboot.handler.RestResponseExceptionHandler;
import com.paschoalini.springboot.models.PageableResponse;
import com.paschoalini.springboot.models.Student;

public class JavaClientDAO {
	private RestTemplate restTemplate = new RestTemplateBuilder()
			.rootUri("http://localhost:8080/v1/protected/students")
			.basicAuthentication("glauber@gmail.com", "usuario")
			.errorHandler(new RestResponseExceptionHandler())
			.build();
	
	private RestTemplate restTemplateAdmin = new RestTemplateBuilder()
			.rootUri("http://localhost:8080/v1/admin/students")
			.basicAuthentication("paschoalini@gmail.com", "administrador")
			.errorHandler(new RestResponseExceptionHandler())
			.build();
	
	public Student findById(Long id) {
		return restTemplate.getForObject("/{id}", Student.class, id);
	}
	
	public List<Student> listAll() {
		ResponseEntity<PageableResponse<Student>> exchange = restTemplate.exchange("/", HttpMethod.GET, null,
				new ParameterizedTypeReference<PageableResponse<Student>>() {});
		return exchange.getBody().getContent();
	}
	
	public Student save(Student student) {
		ResponseEntity<Student> exchange = restTemplateAdmin.exchange("/",
				HttpMethod.POST, new HttpEntity<>(student, createJSONHeader()), Student.class);
		return exchange.getBody();
	}
	
	public void update(Student student) {
		restTemplateAdmin.put("/", student);
	}
	
	public void delete(Long id) {
		restTemplateAdmin.delete("/{id}", id);
	}
	
	private static HttpHeaders createJSONHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}
