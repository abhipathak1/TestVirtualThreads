package com.hyperface.vt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestingVirtualThreadsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestingVirtualThreadsApplication.class, args);
	}

	@Bean
	public CommandLineRunner runTest() {
		return args -> {
			new TestVirtualThreads().runTest();
		};
	}
}
