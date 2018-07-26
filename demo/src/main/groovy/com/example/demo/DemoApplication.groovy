package com.example.demo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@EnableWebSecurity
@EnableAutoConfiguration
@EnableScheduling
@SpringBootApplication
class DemoApplication {

	static void main(String[] args) {
		SpringApplication.run DemoApplication, args
	}
}
