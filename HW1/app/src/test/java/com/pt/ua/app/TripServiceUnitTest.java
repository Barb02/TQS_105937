package com.pt.ua.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pt.ua.app.domain.*;
import com.pt.ua.app.repository.TripRepository;
import com.pt.ua.app.service.impl.TripServiceImpl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TripServiceUnitTest {

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private TripServiceImpl service;

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
        aveiroPorto2 = new Trip(aveiro, porto, 10, time2, 20);
        aveiroLisboa = new Trip(aveiro, lisboa, 15, time3, 25);

        List<Trip> allTrips = Arrays.asList(aveiroPorto1, aveiroPorto2, aveiroLisboa);
    }

}
