package com.epsi.MSPR;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.epsi.MSPR")
public class MsprApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsprApplication.class, args);
	}

}
