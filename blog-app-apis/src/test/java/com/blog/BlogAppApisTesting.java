package com.blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blog.repositories.UserRepo;

@SpringBootTest
public class BlogAppApisTesting {
	
	@Autowired
	private UserRepo userRepo;
	
	@Test
	public void repoTest() {
		System.out.println(this.userRepo.getClass().getName());
		System.out.println(this.userRepo.getClass().getPackageName());
	}

}
