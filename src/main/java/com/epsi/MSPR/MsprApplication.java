package com.epsi.MSPR;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.epsi.MSPR"})
@EnableJpaRepositories(basePackages = "com.epsi.MSPR.repository")
@EntityScan(basePackages = "com.epsi.MSPR.model")
public class MsprApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsprApplication.class, args);
	}

}
