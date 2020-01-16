package com.paschoalini.springboot;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
 * Até ter o Endpoint para cadastrar usuários, o cadastro é feito manualmente
 * no MySQL e, portanto, é necessário criar a versão criptografada das senhas.
 * Por isso criamos esta classe.
 */
public class PasswordEncoder {
	public static void main(String[] args) {
		BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
		System.out.println("usuario......: " + pe.encode("usuario"));
		System.out.println("administrador: " + pe.encode("administrador"));
	}
}
