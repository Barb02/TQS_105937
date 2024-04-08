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
import com.pt.ua.app.repository.ReservationRepository;
import com.pt.ua.app.repository.TripRepository;
import com.pt.ua.app.service.impl.ReservationServiceImpl;
import com.pt.ua.app.dto.ReservationRequest;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceUnitTest {
    
    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private City aveiro;
    private City porto;
    private Trip aveiroPorto1;
    private ReservationRequest reservationRequest;

    @BeforeEach
    public void setUp() throws Exception {
        aveiro = new City("Aveiro");
        porto = new City("Porto");

        LocalDateTime time1 = LocalDateTime.of(2024, 04, 05, 10, 00);

        aveiroPorto1 = new Trip(aveiro, porto, 10, time1, 20);
        aveiroPorto1.setId(1L);
        reservationRequest = new ReservationRequest(aveiroPorto1.getId(), "John Doe", "Rua do Almada", "912345678", "4000-069", "1234567890123456", "12", "2024", "123", "John Doe", 2);
    }

    @Test
    void givenTrips_whenBookReservation_thenDecreaseSeats() throws Exception{
        Mockito.when(tripRepository.findById(aveiroPorto1.getId())).thenReturn(java.util.Optional.of(aveiroPorto1));

        reservationService.createReservation(reservationRequest);

        assertThat(aveiroPorto1.getSeats()).isEqualTo(18);

        Mockito.verify(tripRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
        Mockito.verify(reservationRepository, VerificationModeFactory.times(1)).save(Mockito.any(Reservation.class));
    }
}
