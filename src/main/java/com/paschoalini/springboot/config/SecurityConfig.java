package com.paschoalini.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/* A API toda será protegida */
		http.authorizeRequests()
		.anyRequest().authenticated()
		.and()
		.httpBasic()
		.and()
		.csrf().disable();
	}

	/*
	 * Este é o método que executa a autenticação ANTES de executar o endpoint
	 * foi adicionado o {noop} como prefixo da senha para evitar o erro
	 * There is no PasswordEncoder mapped for the id "null"
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		.withUser("glauber").password("{noop}usuario").roles("USER")
		.and()
		.withUser("paschoalini").password("{noop}administrador").roles("USER", "ADMIN");
	}
}
