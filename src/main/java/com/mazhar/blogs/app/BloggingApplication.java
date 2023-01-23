package com.mazhar.blogs.app;

import com.mazhar.blogs.app.config.AppConstants;
import com.mazhar.blogs.app.entities.Roles;
import com.mazhar.blogs.app.repositories.RolesRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class BloggingApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RolesRepo rolesRepo;

	public static void main(String[] args) {
		SpringApplication.run(BloggingApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("saif123"));
		//$2a$10$A36gi3jeJkd8fHiJO/kGqeybf8GRt8DWnRL6sJ37JoWeVMqB/E7cS

		System.out.println("This is for user mazhar"+this.passwordEncoder
				.encode("mazhar123"));
		//Roles to create

		try {
			Roles role1 =new Roles();
			role1.setId(AppConstants.ROLE_ADMIN);
			role1.setName("ROLE_ADMIN");

			Roles role2 =new Roles();
			role2.setId(AppConstants.ROLE_NORMAL);
			role2.setName("ROLE_NORMAL");

			List<Roles> roles = List.of(role1,role2);

			this.rolesRepo.saveAll(roles);

			roles.forEach(r -> System.out.println(r.getName()));
		} catch (Exception e) {
			System.out.println("Roles column is not inserted");
		}


	}
}
