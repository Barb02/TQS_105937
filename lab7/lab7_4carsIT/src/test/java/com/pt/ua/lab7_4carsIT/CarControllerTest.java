package com.pt.ua.lab7_4carsIT;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.pt.ua.lab7_4carsIT.controller.CarController;
import com.pt.ua.lab7_4carsIT.domain.Car;
import com.pt.ua.lab7_4carsIT.service.CarManagerService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mvc;  

    @MockBean
    private CarManagerService service;


    @BeforeEach
    public void setUp() throws Exception {
    }

    @Test
    void whenPostCar_thenCreateCar( ) throws Exception {
        Car jeep = new Car("Jeep", "Gladiator");

        when( service.save(Mockito.any()) ).thenReturn(jeep);

        mvc.perform(
                post("/api/cars").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(jeep)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is("Jeep")));

        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void givenManyCars_whenGetCars_thenReturnJsonArray() throws Exception {
        Car jeep = new Car("Jeep", "Gladiator");
        Car uno = new Car("Fiat", "Uno");
        Car celta = new Car("Chevrolet", "Celta");

        List<Car> allCars = Arrays.asList(jeep, uno, celta);

        when( service.getAllCars()).thenReturn(allCars);

        mvc.perform(
                get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].model", is(jeep.getModel())))
                .andExpect(jsonPath("$[1].model", is(uno.getModel())))
                .andExpect(jsonPath("$[2].model", is(celta.getModel())));

        verify(service, times(1)).getAllCars();
    }

    @Test
    void givenId_whenGetCarById_thenReturnJson() throws Exception {
        Car jeep = new Car("Jeep", "Gladiator");
        jeep.setCarId(1L);

        when(service.getCarDetails(1L)).thenReturn(jeep);

        mvc.perform(
                get("/api/cars/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maker", is(jeep.getMaker())));

        verify(service, times(1)).getCarDetails(1L);
    }
}

