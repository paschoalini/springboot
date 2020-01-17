package com.paschoalini.springboot.javaclient;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.paschoalini.springboot.models.Student;

public class JavaSpringClientTest {
	public static void main(String[] args) {
		String username = "glauber@gmail.com";
		String password = "usuario";
		
		RestTemplate restTemplate = new RestTemplateBuilder()
				.rootUri("http://localhost:8080/v1/protected/students")
				.basicAuthentication(username, password)
				.build();
		
		Student student = restTemplate.getForObject("/{id}", Student.class, 17);
	
		System.out.println(student.getId());
		System.out.println(student.getName());
		System.out.println(student.getEmail());
		
		ResponseEntity<Student> forEntity = restTemplate.getForEntity("/{id}", Student.class, 17);
		System.out.println("\nUm único estudante");
		System.out.println(forEntity);
		System.out.println(forEntity.getBody());
		
		System.out.println("\nVarios estudantes");
		Student[] students = restTemplate.getForObject("/", Student[].class);
		System.out.println(Arrays.toString(students));
		ResponseEntity<List<Student>> exchange = restTemplate.exchange("/", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Student>>() {});
		System.out.println(exchange.getBody());
		for(Student s : exchange.getBody())
			System.out.println(s);
	}
}
