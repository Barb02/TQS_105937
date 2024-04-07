package com.pt.ua.app;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.pt.ua.app.controller.TripController;
import com.pt.ua.app.service.TripService;
import com.pt.ua.app.domain.Trip;
import com.pt.ua.app.domain.City;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDateTime;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import static org.hamcrest.Matchers.*;

@WebMvcTest(TripController.class)
public class TripControllerWithMockServiceTest {
    
    @Autowired
    private MockMvc mvc;  

    @MockBean
    private TripService service;

    private List<City> allCities;
    private City aveiro;
    private City porto;
    private City lisboa;
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
    }

    @Test
    void givenSomeCities_whenGetCities_thenReturnAllCities() throws Exception {

        allCities = Arrays.asList(aveiro, porto, lisboa);

        when(service.getAllCities()).thenReturn(allCities);

        RestAssuredMockMvc
        .given()
            .mockMvc(mvc)
        .when()
            .get("/api/v1/cities")
        .then()
            .statusCode(200)
            .body("name", contains("Aveiro", "Porto", "Lisboa"));

        verify(service, times(1)).getAllCities();
    }

    @Test
    void givenCityWithAvailableDestination_whenGetDestinationCities_returnDestinationCities() throws Exception {

        List<City> destinationCities = Arrays.asList(porto, lisboa);

        when(service.getCityById(1L)).thenReturn(aveiro);
        when(service.getDestinationCities(aveiro)).thenReturn(destinationCities);

        RestAssuredMockMvc
        .given()
            .mockMvc(mvc)
        .when()
            .get("/api/v1/cities/1/destinations")
        .then()
            .statusCode(200)
            .body("name", contains("Porto", "Lisboa"));

        verify(service, times(1)).getDestinationCities(aveiro);
    }

    @Test
    void givenCityWithoutAvailableDestinations_whenGetDestinationCities_returnEmptyList() throws Exception {

        when(service.getCityById(3L)).thenReturn(lisboa);
        when(service.getDestinationCities(lisboa)).thenReturn(new ArrayList<>());

        RestAssuredMockMvc
        .given()
            .mockMvc(mvc)
        .when()
            .get("/api/v1/cities/1/destinations")
        .then()
            .statusCode(200)
            .body("", hasSize(0));

        verify(service, times(1)).getDestinationCities(Mockito.any());
    }

    @Test
    void givenTrips_whenGetTrips_thenReturnTrips() throws Exception {

        List<Trip> trips = Arrays.asList(aveiroPorto1, aveiroPorto2);

        when(service.getTrips(aveiro, porto, LocalDateTime.of(2024, 04, 05, 00, 00), LocalDateTime.of(2024, 04, 05, 23, 59), "EUR")).thenReturn(trips);
        when(service.getCityById(1L)).thenReturn(aveiro);
        when(service.getCityById(2L)).thenReturn(porto);

        RestAssuredMockMvc
        .given()
            .mockMvc(mvc)
        .when()
            .get("api/v1/trips?originId=1&destinationId=2&startDate=2024-04-05T00:00:00&endDate=2024-04-05T23:59:00&currency=EUR")
        .then()
            .statusCode(200)
            .body("city1.name", contains("Aveiro", "Aveiro"))
            .body("city2.name", contains("Porto", "Porto"))
            .body("price", contains(10.0f, 11.0f))
            .body("dateTime", contains("2024-04-05T10:00:00", "2024-04-05T11:00:00"))
            .body("seats", contains(20, 20));

        verify(service, times(1)).getTrips(aveiro, porto, LocalDateTime.of(2024, 04, 05, 00, 00), LocalDateTime.of(2024, 04, 05, 23, 59), "EUR");
        verify(service, times(2)).getCityById(Mockito.any());
    }
} 
