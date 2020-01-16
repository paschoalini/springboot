package com.paschoalini.springboot.models;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Entity
public class Student extends AbstractEntity {
	private static final long serialVersionUID = 1L; // Precisa? Já não vem importado?
	
	@NotEmpty(message = "O campo nome do estudante é obrigatório.")
	private String name;
	
	@NotEmpty(message = "O campo email do estudante é obrigatório.")
	@Email(message = "O email informado não é válido.")
	private String email;
	
	public Student() {
	}
	
	public Student(String name) {
		this.name = name;
	}
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
