package com.pt.ua.lab7_4carsIT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.hamcrest.Matchers.*;

import com.pt.ua.lab7_4carsIT.domain.Car;
import com.pt.ua.lab7_4carsIT.repository.CarRepository;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create")
public class CarAPITestContainersIT {
    
    @Container
	public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:16")
            .withUsername("user")
            .withPassword("password")
            .withDatabaseName("lab7db");

    @LocalServerPort
    int localPortForTestServer;
    Car gladiator, uno, celta;

    @Autowired
    private CarRepository repository;

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);

    }

    @BeforeEach
    void setupCars() throws Exception {
        gladiator = new Car("Jeep", "Gladiator");
        uno = new Car("Fiat", "Uno");
        celta = new Car("Chevrolet", "Celta");
        repository.save(gladiator);
        repository.save(uno);
        repository.save(celta);
    }

    @Test
    void whenGetCarsById_thenReturnCar(){
        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(localPortForTestServer)
                .pathSegment("api", "cars", String.valueOf( gladiator.getCarId()) )
                .build()
                .toUriString();

        RestAssured
        .given()
            .when()
            .get(endpoint)
        .then()
            .statusCode(200)
            .body("maker", equalTo("Jeep"))
            .body("model", equalTo("Gladiator"));
    }

    @Test
    void whenGetCars_thenReturnAllCars(){
        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(localPortForTestServer)
                .pathSegment("api", "cars")
                .build()
                .toUriString();

        RestAssured
        .given()
            .when()
            .get(endpoint)
        .then()
            .statusCode(200)
            .body("", hasSize(3))
            .body("model", hasItems("Gladiator", "Uno", "Celta"))
            .body("maker", hasItems("Jeep", "Fiat", "Chevrolet"));
    }

    @Test
    void whenGetCarByInvalidId_thenReturn404(){
        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(localPortForTestServer)
                .pathSegment("api", "cars", "4")
                .build()
                .toUriString();

        RestAssured
        .given()
            .when()
            .get(endpoint)
        .then()
            .statusCode(404);
    }

    @Test
    void whenValidInput_thenCreateCar() throws Exception{

        Car renegade = new Car("Jeep", "Renegade");

        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(localPortForTestServer)
                .pathSegment("api", "cars")
                .build()
                .toUriString();

        RestAssured
        .given()
            .body(JsonUtils.toJson(renegade))
            .header("Content-type", "application/json")
        .when()
            .post(endpoint)
        .then()
            .statusCode(201)
            .body("model", equalTo("Renegade"))
            .body("maker", equalTo("Jeep"));
    }

}
