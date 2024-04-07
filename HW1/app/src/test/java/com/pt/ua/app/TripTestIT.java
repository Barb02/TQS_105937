package com.pt.ua.app;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.pt.ua.app.repository.CityRepository;
import com.pt.ua.app.repository.TripRepository;

import io.restassured.RestAssured;

import com.pt.ua.app.domain.City;
import com.pt.ua.app.domain.Trip;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import io.restassured.RestAssured;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TripTestIT {
    
    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private CityRepository cityRepository;

    private City aveiro, aveiroSaved;
    private City porto, portoSaved;
    private City lisboa, lisboaSaved;
    private Trip aveiroPorto1;
    private Trip aveiroPorto2;
    private Trip aveiroLisboa;

    @BeforeEach
    public void setUp() throws Exception {
        aveiro = new City("Aveiro");
        porto = new City("Porto");
        lisboa = new City("Lisboa");

        LocalDateTime time1 = LocalDateTime.of(2024, 04, 05, 10, 00);
        LocalDateTime time2 = LocalDateTime.of(2024, 04, 05, 11, 00);
        LocalDateTime time3 = LocalDateTime.of(2024, 04, 05, 12, 00);

        aveiroPorto1 = new Trip(aveiro, porto, 10, time1, 20);
        aveiroPorto2 = new Trip(aveiro, porto, 11, time2, 20);
        aveiroLisboa = new Trip(aveiro, lisboa, 15, time3, 25);

        aveiroSaved = cityRepository.save(aveiro);
        portoSaved = cityRepository.save(porto);
        lisboaSaved = cityRepository.save(lisboa);
        tripRepository.save(aveiroPorto1);
        tripRepository.save(aveiroPorto2);
        tripRepository.save(aveiroLisboa);
    }

    @Test
    void givenTrips_whenGetTrips_thenReturnTrips_NotEUR() {
        String endpoint = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("127.0.0.1")
                .port(randomServerPort)
                .pathSegment("api", "v1", "trips")
                .queryParam("originId", aveiroSaved.getId()) 
                .queryParam("destinationId", portoSaved.getId())
                .queryParam("startDate", "2024-04-05T00:00") 
                .queryParam("endDate", "2024-04-05T23:59")
                .queryParam("currency", "USD")
                .build()
                .toUriString();

        RestAssured
        .given()
            .when()
            .get(endpoint)
        .then()
            .statusCode(200)
            .body("city1.name", contains("Aveiro", "Aveiro"))
            .body("city2.name", contains("Porto", "Porto"))
            .body("dateTime", contains("2024-04-05T10:00:00", "2024-04-05T11:00:00"))
            .body("seats", contains(20, 20))
            .body("price[0]", both(not(equalTo(10.0f))).and(greaterThan(0.0f))) 
            .body("price[1]", both(not(equalTo(11.0f))).and(greaterThan(0.0f)));
    }

}
