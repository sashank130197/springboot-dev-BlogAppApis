package com.blog.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.exceptions.*;
import com.blog.config.AppConstants;
import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.payloads.UserDto;
import com.blog.repositories.RoleRepo;
import com.blog.repositories.UserRepo;
import com.blog.services.UserService;



@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepo roleRepo;
	@Override
	public UserDto createUser(UserDto userDto) {
		// TODO Auto-generated method stub
		
		User user=this.dtoToUser(userDto);
		User savedUser=this.userRepo.save(user);
		return this.UserToUserDto(savedUser);
		
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer UserId) {
		// TODO Auto-generated method stub
		
		User user=this.userRepo.findById(UserId).orElseThrow(()-> new ResourceNotFoundException("User","Id",UserId));
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		User updatedUser=this.userRepo.save(user);
		UserDto userDTO =this.UserToUserDto(updatedUser);
		return userDTO;
	}

	@Override
	public UserDto getUserById(Integer UserId) {
		
		User user=this.userRepo.findById(UserId).orElseThrow(()-> new ResourceNotFoundException("User","Id",UserId));
		
		// TODO Auto-generated method stub
		return this.UserToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
	List<User> users=this.userRepo.findAll();
	List<UserDto> userDto=users.stream().map(user->this.UserToUserDto(user) ).collect(Collectors.toList());
		return userDto;
	}

	@Override
	public void deleteUser(Integer userId) {
		User  user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","Id",userId));
		this.userRepo.delete(user);
		
		
		// TODO Auto-generated method stub

	}
	/* COMMENTING MANUAL IMPLEMENTATION IF NOT USING MODEL MAPPER
	private User dtoToUser(UserDto userDto) {
		User user=new User();
		user.setId(userDto.getId());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		return user;
		
	}
	
	
	public UserDto UserToUserDto(User user) {
		UserDto userDto=new UserDto();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		userDto.setAbout(user.getAbout());
		return userDto;
		
	}
*/
	
	
	private User dtoToUser(UserDto userDto) {
		User user =this.modelMapper.map(userDto, User.class);
		return user;
		
		
	}
	
	
	public UserDto UserToUserDto(User user) {
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		return userDto;
		
	}

	@Override
	public UserDto registerNewUser(UserDto userdto) {
	User user=	this.modelMapper.map(userdto, User.class);
	//encode the users password
	user.setPassword(this.passwordEncoder.encode(user.getPassword())); 
	
	
	// roles
	// all user registering through registerUser by default will be normal users
	
	Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
	user.getRoles().add(role);
	User newUser=this.userRepo.save(user);
    return this.modelMapper.map(newUser,UserDto.class);
    }
	
}
