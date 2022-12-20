package com.sp.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sp.project.mapper")
public class InitialBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(InitialBackendApplication.class, args);
	}

}
