package com.paschoalini.springboot;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.paschoalini.springboot.models.Student;
import com.paschoalini.springboot.repository.StudentRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class StudentRepositoryTest {
	@Autowired
	private StudentRepository studentRepository;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	/*
	 * Primeiro teste
	 * save deve persistir no BD
	 */
	@Test
	public void createShouldPersistData() {
		Student s = new Student("Glauber", "glauber@provedor.com.br");
		this.studentRepository.save(s);
		Assertions.assertThat(s.getId()).isNotNull();
		Assertions.assertThat(s.getName()).isEqualTo("Glauber");
		Assertions.assertThat(s.getEmail()).isEqualTo("glauber@provedor.com.br");
	}
	
	/*
	 * Segundo teste
	 * delete deve remover do BD
	 */
	@Test
	public void deleteShouldRemoveData() {
		Student s = new Student("Glauber", "glauber@provedor.com.br");
		this.studentRepository.save(s);
		studentRepository.delete(s);
		Optional<Student> os = studentRepository.findById(s.getId());
		Assertions.assertThat(os.isPresent()).isFalse();
	}
	
	/*
	 * Terceiro teste
	 * update deve alterar e persistir do BD
	 */
	@Test
	public void updateShouldRemoveData() {
		Student s = new Student("Original", "original@provedor.com.br");
		this.studentRepository.save(s);
		s.setName("alterado");
		s.setEmail("alterado@provedor.com.br");
		s = this.studentRepository.save(s);
		Assertions.assertThat(s.getName()).isEqualTo("alterado");
		Assertions.assertThat(s.getEmail()).isEqualTo("alterado@provedor.com.br");
	}
	

	/*
	 * Quarto teste
	 * procurar nome ignorando case com busca parcial
	 */
	@Test
	public void findByNameIgnoreCaseContainingShouldIgnoreCase() {
		Student s1 = new Student("estudante", "original@provedor.com.br");
		Student s2 = new Student("Estudante", "original@provedor.com.br");
		this.studentRepository.save(s1);
		this.studentRepository.save(s2);
		List<Student> studentList = studentRepository.findByNameIgnoreCaseContaining("estudante");
		Assertions.assertThat(studentList.size()).isEqualTo(2);
	}
	
	// Não está funcionando... verificar a causa
	@Test
	public void createWhenNameIsNullShouldThrowConstraintViolationException() {
		thrown.expect(ConstraintViolationException.class);
		thrown.expectMessage("O campo nome do estudante é obrigatório.");
		this.studentRepository.save(new Student(null, "email@provedor.com"));
	}
	
	// Não está funcionando... verificar a causa
	@Test
	public void createWhenEmailIsNullShouldThrowConstraintViolationException() {
		thrown.expect(ConstraintViolationException.class);
		this.studentRepository.save(new Student("nome", null));
	}
	
	@Test
	public void createWhenEmailIsNotValidShouldThrowConstraintViolationException() {
		thrown.expect(ConstraintViolationException.class);
		this.studentRepository.save(new Student("nome", "email"));
	}
}
