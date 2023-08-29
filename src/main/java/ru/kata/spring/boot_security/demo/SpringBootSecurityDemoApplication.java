package ru.kata.spring.boot_security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@ComponentScan(basePackages = "ru.kata.spring.boot_security.demo.configs")
@EntityScan(basePackages ="ru.kata.spring.boot_security.demo.model")
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "ru.kata.spring.boot_security.demo.Repositories")
public class SpringBootSecurityDemoApplication {


	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
	}

/*	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {

		System.out.println("hello world, I have just started up");


	}*/
}
