package com.pt.ua.app;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.pt.ua.app.domain.*;
import com.pt.ua.app.dto.ReservationRequest;
import com.pt.ua.app.service.ReservationService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

import com.pt.ua.app.controller.ReservationController;

@WebMvcTest(ReservationController.class)
public class ReservationControllerWithMockService {
    
    @Autowired
    private MockMvc mvc;  

    @MockBean
    private ReservationService service;

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
    void whenCreateCity_thenCityIsCreated() throws Exception {
        ReservationRequest reservationRequest = new ReservationRequest(1L, "João", "Rua do João", "912345678", "3800-000", "1234567890123456", "12", "2024", "123", "João", 2);
        Reservation reservation = new Reservation(reservationRequest);
        when(service.createReservation(reservationRequest)).thenReturn(reservation);

        RestAssuredMockMvc
        .given()
            .mockMvc(mvc)
            .body(JsonUtils.toJson(reservationRequest))
            .header("Content-type", "application/json")
        .when()
            .post("/api/v1/reservations")
        .then()
            .statusCode(201);

        verify(service, times(1)).createReservation(Mockito.any());
    }
}
