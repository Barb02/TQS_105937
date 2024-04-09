package com.pt.ua.app;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;


import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.pt.ua.app.domain.City;
import com.pt.ua.app.domain.Reservation;
import com.pt.ua.app.domain.Trip;
import com.pt.ua.app.dto.ReservationRequest;
import com.pt.ua.app.repository.*;

import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=create")
@ActiveProfiles("test")
public class ReservationTestIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private TripRepository tripRepository;

    @Container
	public static MySQLContainer<?> container = new MySQLContainer<>("mysql:latest")
            .withUsername("user")
            .withPassword("password")
            .withDatabaseName("testdb");
    
    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }
    
    ReservationRequest reservationRequest, reservationRequestToBePosted;
    Reservation reservation;
    private City aveiro;
    private City porto;
    private Trip aveiroPorto1, tripSaved;

    @AfterEach
    public void tearDown() {
        reservationRepository.deleteAll();
    }

    @BeforeEach
    public void setUp() throws Exception {
        aveiro = new City("Aveiro");
        porto = new City("Porto");

        LocalDateTime time1 = LocalDateTime.of(2024, 04, 05, 10, 00);

        aveiroPorto1 = new Trip(aveiro, porto, 10, time1, 20);

        reservationRequest = new ReservationRequest(1L, "João", "Rua do João", "912345678", "3800-000", "1234567890123456", "12", "2024", "123", "João", 2);
        reservation = new Reservation(reservationRequest);
        reservation.setToken(UUID.fromString("b1c4577b-0c71-40f6-b42a-d30461792708"));

        reservationRepository.save(reservation);

        cityRepository.save(aveiro);
        cityRepository.save(porto);
        tripSaved = tripRepository.save(aveiroPorto1);

        reservationRequestToBePosted = new ReservationRequest(tripSaved.getId(), "Maria", "Rua do João", "12312340", "0000-000", "1234567890123456", "12", "2024", "123", "Maria", 1);
    }

    @Test
    void whenCreateReservation_thenReservationIsCreated() throws Exception {

        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(randomServerPort)
                .pathSegment("api", "v1", "reservations")
                .build()
                .toUriString();

        RestAssured
        .given()
            .body(JsonUtils.toJson(reservationRequestToBePosted))
            .header("Content-type", "application/json")
        .when()
            .post(endpoint)
        .then()
            .statusCode(201);

    }

    @Test
    void givenReservation_whenGetReservation_thenReturnReservation(){

        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(randomServerPort)
                .pathSegment("api", "v1", "reservations", "b1c4577b-0c71-40f6-b42a-d30461792708")
                .build()
                .toUriString();

        RestAssured
        .given()
        .when()
            .get(endpoint)
        .then()
            .statusCode(200)
            .body("name", is("João"));
    }
}
