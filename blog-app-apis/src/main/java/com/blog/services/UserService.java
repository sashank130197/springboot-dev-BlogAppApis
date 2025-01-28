package com.blog.services;

import java.util.List;

import com.blog.entities.User;
import com.blog.payloads.UserDto;

public interface UserService {
	
	
	UserDto registerNewUser(UserDto userdto);
	
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Integer UserId);
	UserDto getUserById(Integer UserId);
	
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId); 

}
