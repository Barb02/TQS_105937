package com.pt.ua.app;

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
import static org.hamcrest.Matchers.*;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

import com.pt.ua.app.controller.ReservationController;

@WebMvcTest(ReservationController.class)
class ReservationControllerWithMockService {
    
    @Autowired
    private MockMvc mvc;  

    @MockBean
    private ReservationService service;

    ReservationRequest reservationRequest;
    Reservation reservation;

    @BeforeEach
    public void setUp() throws Exception {
        reservationRequest = new ReservationRequest(1L, "João", "Rua do João", "912345678", "3800-000", "1234567890123456", "12", "2024", "123", "João", 2);
        reservation = new Reservation(reservationRequest);
    }

    @Test
    void whenCreateReservation_thenReservationIsCreated() throws Exception {

        when(service.createReservation(Mockito.any())).thenReturn(reservation);

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

    @Test
    void givenReservation_whenGetReservation_thenReturnReservation(){

        when(service.getReservationByToken(Mockito.any())).thenReturn(reservation);

        RestAssuredMockMvc
        .given()
            .mockMvc(mvc)
        .when()
            .get("/api/v1/reservations/c1c4577b-0c71-40f6-b42a-d30461792708")
        .then()
            .statusCode(200)
            .body("name", equalTo("João"));

        verify(service, times(1)).getReservationByToken(Mockito.any());
    }
}
