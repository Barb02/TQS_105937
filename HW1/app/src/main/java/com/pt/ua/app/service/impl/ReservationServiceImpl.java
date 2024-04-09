package com.pt.ua.app.service.impl;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pt.ua.app.repository.ReservationRepository;
import com.pt.ua.app.repository.TripRepository;
import com.pt.ua.app.domain.Reservation;
import com.pt.ua.app.dto.ReservationRequest;
import com.pt.ua.app.service.ReservationService;
import com.pt.ua.app.domain.Trip;

@Service
public class ReservationServiceImpl implements ReservationService{

    private static final Logger log = LoggerFactory.getLogger(ReservationServiceImpl.class);

    private final ReservationRepository reservationRepository;
    private final TripRepository tripRepository;

    public ReservationServiceImpl(ReservationRepository reservationRepository, TripRepository tripRepository) {
        this.reservationRepository = reservationRepository;
        this.tripRepository = tripRepository;
    }

    @Override
    public Reservation createReservation(ReservationRequest reservationRequest){
        Reservation reservation = new Reservation(reservationRequest);
        Trip trip = tripRepository.findById(reservationRequest.getTripId()).orElse(null);

        if(trip == null){
            log.error("Trip not found: " + reservationRequest.getTripId());
            return null;
        }

        if(trip.getSeats() - reservationRequest.getNumberOfTickets() < 0){
            log.error("Not enough seats available for trip: " + reservationRequest.getTripId());
            return null;
        }

        trip.setSeats(trip.getSeats() - reservationRequest.getNumberOfTickets());
        reservation.setTrip(trip);
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation getReservationByToken(UUID token){
        return reservationRepository.findByToken(token);
    }
}
