package com.paschoalini.springboot.javaclient;

import com.paschoalini.springboot.models.Student;

public class JavaSpringClientTest {
	public static void main(String[] args) {
		JavaClientDAO bd = new JavaClientDAO();
		
		System.out.println("\n\n=====Listando todos estudantes");
		for(Student s : bd.listAll())
			System.out.println(s);
		
		System.out.println("\n\n=====Listando UM estudante");
		System.out.println(bd.findById(23L));
		
		
		System.out.println("Salvando um estudante");
		Student s = new Student();
		s.setName("João da Silva");
		s.setEmail("jsilva@gmail.com");
		System.out.println(bd.save(s));
		
	}
}
