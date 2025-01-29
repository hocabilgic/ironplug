package com.ironplug;




import com.ironplug.entity.enums.RoleType;
import com.ironplug.entity.user.UserRole;
import com.ironplug.payload.request.user.UserRequest;
import com.ironplug.repository.User.UserRoleRepository;
import com.ironplug.service.user.UserRoleService;
import com.ironplug.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class ironplugApplication implements CommandLineRunner {

	private final UserRoleService userRoleService;

	private final UserRoleRepository userRoleRepository;

	private final UserService userService;

	public ironplugApplication(UserRoleService userRoleService,
							UserRoleRepository userRoleRepository,
							UserService userService) {

		this.userRoleService = userRoleService;
		this.userRoleRepository = userRoleRepository;
		this.userService = userService;

	}

	public static void main(String[] args) {
		SpringApplication.run(ironplugApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

		if (userRoleService.getAllUserRole().isEmpty()) {

			UserRole admin = new UserRole();
			admin.setRoleType(RoleType.ADMIN);
			admin.setName("Admin");

			userRoleRepository.save(admin);


			UserRole customer = new UserRole();
			customer.setRoleType(RoleType.CUSTOMER);
			customer.setName("Customer");
			userRoleRepository.save(customer);
		}

		if (userService.countAllAdmins() == 0) {

			Set<String> roles = new HashSet<>();
			roles.add("Admin");
			UserRequest adminRequest = new UserRequest();
			adminRequest.setEmail("deneme@github.com");
			adminRequest.setSifre("123456789");
			adminRequest.setFirst_name("Deneme");
			adminRequest.setLast_name("Api");

			userService.saveAdmin(adminRequest);


		}


	}
}

