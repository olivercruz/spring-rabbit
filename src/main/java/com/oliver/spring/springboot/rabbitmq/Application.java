package com.oliver.spring.springboot.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public Application(){
		//Para Spring
	}

	public static void main(String[] args) {
		new SpringApplication(Application.class).run(args);
	}
}
