package com.astles.addressbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication()
@ComponentScan()
public class AddressbookApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AddressbookApplication.class, args);
	}

}
