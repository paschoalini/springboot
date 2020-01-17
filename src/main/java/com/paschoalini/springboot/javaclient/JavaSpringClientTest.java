package com.paschoalini.springboot.javaclient;

import com.paschoalini.springboot.models.Student;

public class JavaSpringClientTest {
	public static void main(String[] args) {
		JavaClientDAO bd = new JavaClientDAO();
		
		/*
		System.out.println("\n\n=====Listando todos estudantes");
		for(Student s : bd.listAll())
			System.out.println(s);
		*/
		System.out.println("\n\n=====Listando UM estudante");
		System.out.println(bd.findById(230L));
		/*	
		System.out.println("Salvando um estudante");
		Student s = new Student();
		s.setName("João da Silva");
		s.setEmail("jsilva@gmail.com");
		System.out.println(bd.save(s));
		
		
		Student s = bd.findById(23L);
		System.out.println("Antes da alteração");
		System.out.println(s);
		
		s.setName("Alterado");
		s.setEmail("alterado@uol.com.br");
		bd.update(s);
		
		System.out.println("Após da alteração");
		System.out.println(bd.findById(23L));
		*/
		
		/*
		try {
			System.out.println(bd.findById(41L));
			bd.delete(41L);
			System.out.println(bd.findById(41L));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		*/
	}
}
