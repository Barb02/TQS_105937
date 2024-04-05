package com.pt.ua.lab7_3test_containers;

import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.junit.jupiter.Container;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
class Lab73testContainersApplicationTests {

	@Container
	public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16")
            .withUsername("user")
            .withPassword("password")
            .withDatabaseName("lab7db");

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
	@Order(1)
	void contextLoads() {
		Book book = new Book();
		book.setName("How to rizz 101");
		book.setAuthor("Luna");
		bookRepository.save(book);
		System.out.println("Context loads!");
	}

	@Test
	@Order(2)
	void getBooks() {
		List<Book> books = bookRepository.findAll();
		assertThat(books).hasSize(2).extracting(Book::getName).contains("How to rizz 101", "The Hobbit");
	}

}
