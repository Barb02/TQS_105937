package com.pt.ua.lab7_2cars;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.pt.ua.lab7_2cars.controller.CarController;
import com.pt.ua.lab7_2cars.domain.Car;
import com.pt.ua.lab7_2cars.service.CarManagerService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


@WebMvcTest(CarController.class) 
public class CarControllerRestAssuredTest {

    @Autowired
    private MockMvc mvc;  

    @MockBean
    private CarManagerService service;

    @Test
    void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception {
        Car jeep = new Car("Jeep", "Gladiator");
        Car uno = new Car("Fiat", "Uno");
        Car celta = new Car("Chevrolet", "Celta");

        List<Car> allCars = Arrays.asList(jeep, uno, celta);

        when( service.getAllCars()).thenReturn(allCars);

        RestAssuredMockMvc
        .given()
            .mockMvc( mvc)
        .when()
            .get("/api/cars")
        .then()
            .statusCode(200)
            .body("", hasSize(3))
            .body("model", hasItems("Gladiator", "Uno", "Celta"))
            .body("maker", hasItems("Jeep", "Fiat", "Chevrolet"));


        verify(service, times(1)).getAllCars();
    }

    @Test
    void givenId_whenGetCarById_thenReturnJson() throws Exception {
        Car jeep = new Car("Jeep", "Gladiator");
        jeep.setCarId(1L);

        when(service.getCarDetails(1L)).thenReturn(jeep);

        RestAssuredMockMvc
        .given()
            .mockMvc( mvc)
        .when()
            .get("/api/cars/1")
        .then()
            .statusCode(200)
            .body("model", equalTo("Gladiator"))
            .body("maker", equalTo("Jeep"));

        verify(service, times(1)).getCarDetails(1L);
    }

    @Test
    void whenPostCar_thenCreateCar( ) throws Exception {
        Car jeep = new Car("Jeep", "Gladiator");

        when( service.save(Mockito.any()) ).thenReturn(jeep);

        RestAssuredMockMvc
        .given()
            .mockMvc(mvc)
            .body(JsonUtils.toJson(jeep))
            .header("Content-type", "application/json")
        .when()
            .post("/api/cars")
        .then()
            .statusCode(201)
            .body("model", equalTo("Gladiator"))
            .body("maker", equalTo("Jeep"));

        verify(service, times(1)).save(Mockito.any());
    }
    
}
