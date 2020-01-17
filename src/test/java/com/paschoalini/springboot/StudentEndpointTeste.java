package com.paschoalini.springboot;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.paschoalini.springboot.models.Student;
import com.paschoalini.springboot.repository.StudentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableAutoConfiguration
public class StudentEndpointTeste {
	@Autowired
	private TestRestTemplate restTemplate;
	@LocalServerPort
	private int port;
	
	// Acessa normal, mas na verdade não está gravando nada
	@MockBean
	private StudentRepository studentRepository;
	
	@Autowired
	private MockMvc mockMvc;
	
	@TestConfiguration
	static class Config {
		@Bean
		public RestTemplateBuilder restTemplateBuilder() {
			return new RestTemplateBuilder().basicAuthentication("paschoalini@gmail.com", "administrador");
		}
	}
	
	@Before
	public void setup() {
		Student student = new Student(1L, "Legolas", "legolas@lordoftherings.com");
		BDDMockito.when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
	}
	
	@Test
	public void listStudentsWhenUsernameAndPasswordAreIncorrectShouldReturnStatusCode401() {
		restTemplate = restTemplate.withBasicAuth("1", "1");
		ResponseEntity<String> response = restTemplate.getForEntity("/v1/protected/students", String.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(401);
	}
	
	@Test
	public void getStudentByIdWhenUsernameAndPasswordAreIncorrectedShouldReturnStatusCode401() {
		restTemplate = restTemplate.withBasicAuth("1", "1");
		ResponseEntity<String> response = restTemplate.getForEntity("/v1/protected/students/1", String.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(401);
	}
	
	@Test
	public void listStudentsWhenUsernameAndPasswordAreCorrectShouldReturnStatusCode200() {
		List<Student> students = Arrays.asList(
				new Student(1L, "Legolas", "legolas@lordoftherings.com"),
				new Student(2L, "Aragorn", "aragorn@lordoftherings.com")
		);
		
		BDDMockito.when(studentRepository.findAll()).thenReturn(students);
		ResponseEntity<String> response = restTemplate.getForEntity("/v1/protected/students", String.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
	
	// Não funcionou por conta do Optional. Verificar como corrigir
	@Test
	public void getStudentByIdWhenUsernameAndPasswordAreCorrectedShouldReturnStatusCode200() {
		ResponseEntity<Student> response = restTemplate.getForEntity("/v1/protected/students/1", Student.class);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void getStudentByIdWhenUsernameAndPasswordAndStudentDoesNotExistsAreCorrectedShouldReturnStatusCode404() {
		ResponseEntity<Student> response = restTemplate.getForEntity("/v1/protected/students/{id}", Student.class, -1);
		Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(404);
	}
	
	@Test
	public void deleteWhenUserHasRoleAdminAndStudentExistshoudReturnStatusCode200() {
		Student student = new Student(1L, "Legolas", "legolas@lordoftherings.com");
		BDDMockito.doNothing().when(studentRepository).deleteById(1L);
		ResponseEntity<String> exchange = restTemplate.exchange("/v1/admin/students/{id}", HttpMethod.DELETE, null, String.class, 1L);
		Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(200);
	}
}
