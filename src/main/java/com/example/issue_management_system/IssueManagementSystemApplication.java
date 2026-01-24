package com.example.issue_management_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class IssueManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(IssueManagementSystemApplication.class, args);
	}

}
