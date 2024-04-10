package com.example.spribetask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SpribeTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpribeTaskApplication.class, args);
	}

}
