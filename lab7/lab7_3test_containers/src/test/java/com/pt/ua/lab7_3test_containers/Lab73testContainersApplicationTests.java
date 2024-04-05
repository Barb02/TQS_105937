package com.pt.ua.lab7_3test_containers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.Container;

@Testcontainers
@SpringBootTest
class Lab73testContainersApplicationTests {

	@Container
	public static PostgreSQLContainer container = new PostgreSQLContainer()
		.withUsername("duke")
		.withPassword("password")
		.withDatabaseName("test");

	@Autowired
	private BookRepository bookRepository;

	// requires Spring Boot >= 2.2.6
	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", container::getJdbcUrl);
		registry.add("spring.datasource.password", container::getPassword);
		registry.add("spring.datasource.username", container::getUsername);
	}

	@Test
	void contextLoads() {

		Book book = new Book();
		book.setName("Testcontainers");

		bookRepository.save(book);

		System.out.println("Context loads!");
	}


}
