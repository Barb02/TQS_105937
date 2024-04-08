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
import com.pt.ua.app.service.impl.ExchangeService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class TripServiceUnitTest {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private ExchangeService exchangeService;

    @InjectMocks
    private TripServiceImpl service;

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
    void givenTrips_whenGetTrips_thenReturnTrips_NotEUR() throws Exception{
        Mockito.when(tripRepository.findByCity1AndCity2AndDateTimeBetweenAndSeatsGreaterThan(aveiro, porto, LocalDateTime.of(2024, 04, 05, 00, 00), LocalDateTime.of(2024, 04, 05, 23, 59),0))
                .thenReturn(Arrays.asList(aveiroPorto1, aveiroPorto2));

        Mockito.when(exchangeService.getExchangeRate("USD")).thenReturn(1.0841752568);

        List<Trip> found = service.getTrips(aveiro, porto, LocalDateTime.of(2024, 04, 05, 00, 00), LocalDateTime.of(2024, 04, 05, 23, 59), "USD");

        assertThat(found).hasSize(2);

        assertThat(found.get(0).getCity1().getName()).isEqualTo("Aveiro");
        assertThat(found.get(0).getCity2().getName()).isEqualTo("Porto");
        assertThat(found.get(0).getPrice()).isEqualTo(10*1.0841752568);
        assertThat(found.get(0).getDateTime()).isEqualTo(LocalDateTime.of(2024, 04, 05, 10, 00));

        assertThat(found.get(1).getCity1().getName()).isEqualTo("Aveiro");
        assertThat(found.get(1).getCity2().getName()).isEqualTo("Porto");
        assertThat(found.get(1).getPrice()).isEqualTo(11*1.0841752568);
        assertThat(found.get(1).getDateTime()).isEqualTo(LocalDateTime.of(2024, 04, 05, 11, 00));

        Mockito.verify(tripRepository, VerificationModeFactory.times(1)).findByCity1AndCity2AndDateTimeBetweenAndSeatsGreaterThan(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyInt());
    }

    @Test
    void givenTrips_whenGetTrips_thenReturnTrips_EUR() throws Exception{
        Mockito.when(tripRepository.findByCity1AndCity2AndDateTimeBetweenAndSeatsGreaterThan(aveiro, porto, LocalDateTime.of(2024, 04, 05, 00, 00), LocalDateTime.of(2024, 04, 05, 23, 59),0))
                .thenReturn(Arrays.asList(aveiroPorto1, aveiroPorto2));

        List<Trip> found = service.getTrips(aveiro, porto, LocalDateTime.of(2024, 04, 05, 00, 00), LocalDateTime.of(2024, 04, 05, 23, 59), "EUR");

        assertThat(found).hasSize(2);

        assertThat(found.get(0).getCity1().getName()).isEqualTo("Aveiro");
        assertThat(found.get(0).getCity2().getName()).isEqualTo("Porto");
        assertThat(found.get(0).getPrice()).isEqualTo(10);
        assertThat(found.get(0).getDateTime()).isEqualTo(LocalDateTime.of(2024, 04, 05, 10, 00));

        assertThat(found.get(1).getCity1().getName()).isEqualTo("Aveiro");
        assertThat(found.get(1).getCity2().getName()).isEqualTo("Porto");
        assertThat(found.get(1).getPrice()).isEqualTo(11);
        assertThat(found.get(1).getDateTime()).isEqualTo(LocalDateTime.of(2024, 04, 05, 11, 00));

        Mockito.verify(tripRepository, VerificationModeFactory.times(1)).findByCity1AndCity2AndDateTimeBetweenAndSeatsGreaterThan(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyInt());
    }

}
