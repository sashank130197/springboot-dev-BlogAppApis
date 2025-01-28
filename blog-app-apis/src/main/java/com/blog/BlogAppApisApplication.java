package com.blog;

import java.util.List;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.config.AppConstants;
import com.blog.entities.Role;
import com.blog.repositories.RoleRepo;



@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
@Autowired
	private RoleRepo roleRepo;
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		/// testing to encode password of 1st user to Bencrypt...
		System.out.println(this.passwordEncoder.encode("12345"));
		// when application run 2 roles will be there
		
		try {

			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");

			Role seconduser = new Role();
			seconduser.setId(AppConstants.NORMAL_USER);
			seconduser.setName("ROLE_NORMAL");

			List<Role> roles = List.of(role, seconduser);

			List<Role> result = this.roleRepo.saveAll(roles);

			result.forEach(r -> {
				System.out.println(r.getName());
			});

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
}
