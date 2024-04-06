package com.pt.ua.app.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.pt.ua.app.repository.ReservationRepository;
import com.pt.ua.app.repository.TripRepository;
import com.pt.ua.app.domain.Reservation;
import com.pt.ua.app.dto.ReservationRequest;
import com.pt.ua.app.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService{

    private final ReservationRepository reservationRepository;
    private final TripRepository tripRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, TripRepository tripRepository) {
        this.reservationRepository = reservationRepository;
        this.tripRepository = tripRepository;
    }

    @Override
    public Reservation createReservation(ReservationRequest reservationRequest){
        Reservation reservation = new Reservation(reservationRequest);
        reservation.setTrip(tripRepository.findById(reservationRequest.getTripId()).get());
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation getReservationById(UUID reservationId){
        return reservationRepository.findById(reservationId).get();
    }
}