package com.VenTrix.com.VenTrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class VenTrixApplication {

	public static void main(String[] args) {
		SpringApplication.run(VenTrixApplication.class, args);
	}

}
