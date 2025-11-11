package com.example.apptempo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ApptempoApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApptempoApplication.class, args);
	}
}
