package com.paschoalini.springboot.models;

import javax.persistence.Entity;


@Entity
public class Student extends AbstractEntity {
	private static final long serialVersionUID = 1L; // Precisa? Já não vem importado?
	private String name;
	
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
}
