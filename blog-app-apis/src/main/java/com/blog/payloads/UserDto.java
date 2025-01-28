package com.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.Size;

import com.blog.entities.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;
	@NotEmpty
	@Size(min=4,message="UserName must be of minimum length 4")
	private String name;
	@Email(message="Email id is not valid")
	private String email;
	@NotEmpty
	@Size(min=4,max=10,message="Password must be of length between 4-10")
	private String password;
	@NotEmpty
	private String about;
	
	private Set<RoleDto> roles=new HashSet<>();
}
