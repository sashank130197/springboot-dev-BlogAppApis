package com.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.UserDto;
import com.blog.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	//POST -- CREATE USER
	
	//with help of response Entity we send status along with data
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
	UserDto createdUser=	this.userService.createUser(userDto);
	return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
		
	}
	
	
	
	// PUT-  UPDATE USER
	
	@PutMapping("/{userId}")
	public ResponseEntity <UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer uid){
	UserDto updatedUser=	 this.userService.updateUser(userDto, uid);
	return ResponseEntity.ok(updatedUser);
		
	}
	
	
	
	// DELETE-- DELETE USER
	// ONLY ADMIN CAN DELETE USER SO THIS DELETE USER API SHOULD BE ACCESSED ONLY BY ADMIN NOT BY NORMAL USERS
	//FOR THIS USE PREAUTHORIZE, now now admin users can access this method
/*	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid){
		//instead of ApiResponse in upper line we can give ? inside<> if we dont know return type
		this.userService.deleteUser(uid);
		//we can use map also
		//return new ResponseEntity(Map.of("message","User deleted Successfully"),HttpStatus.OK);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted Successfully",true),HttpStatus.OK);
	}*/
	
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid) {
		this.userService.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);
	}
	
	
	//GET --- GET USER
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser(){
		return ResponseEntity.ok(this.userService.getAllUsers());
		
	}
	
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
		return ResponseEntity.ok(this.userService.getUserById(userId));
		
	}
	

}
