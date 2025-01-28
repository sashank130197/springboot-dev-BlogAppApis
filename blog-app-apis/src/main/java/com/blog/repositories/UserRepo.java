package com.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.User;

//JPA repository will provide all the functionals for User
public interface UserRepo extends JpaRepository<User,Integer>{

	
	Optional<User> findByEmail(String email);
}
