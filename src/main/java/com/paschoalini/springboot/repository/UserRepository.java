package com.paschoalini.springboot.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.paschoalini.springboot.models.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long>{
	User findByUsername(String username);
}
