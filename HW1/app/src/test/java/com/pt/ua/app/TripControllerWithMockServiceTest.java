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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.pt.ua.app.controller.TripController;
import com.pt.ua.app.service.TripService;
import com.pt.ua.app.domain.Trip;
import com.pt.ua.app.domain.City;

import java.util.Arrays;
import java.util.List;
import java.time.LocalDateTime;
import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @BeforeEach
    public void setUp() throws Exception {
        aveiro = new City("Aveiro");
        porto = new City("Porto");
        lisboa = new City("Lisboa");
    }

    @Test
    void givenSomeCities_whenGetCities_thenReturnAllCities() throws Exception {

        allCities = Arrays.asList(aveiro, porto, lisboa);

        when(service.getAllCities()).thenReturn(allCities);

        mvc.perform(
            get("/api/v1/cities").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is(aveiro.getName())))
                .andExpect(jsonPath("$[1].name", is(porto.getName())))
                .andExpect(jsonPath("$[2].name", is(lisboa.getName())));

        verify(service, times(1)).getAllCities();
    }

    /* @Test
    void givenSomeCitiesAndConnections_whenGetDestinationCities_thenReturnAvailableDestinationCities() throws Exception {

        avaiableCities = 

        when(service.getDestinationCities(aveiro, LocalDate.of(2024, 04, 05))).thenReturn(allCities);

        mvc.perform(
            get("/api/v1/cities").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is(aveiro.getName())))
                .andExpect(jsonPath("$[1].name", is(porto.getName())))
                .andExpect(jsonPath("$[2].name", is(lisboa.getName())));

        verify(service, times(1)).getAllCities();
    }
    */
} 
