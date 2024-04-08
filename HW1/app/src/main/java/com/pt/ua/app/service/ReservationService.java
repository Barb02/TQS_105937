package com.pt.ua.app.service;

import com.pt.ua.app.domain.Reservation;
import com.pt.ua.app.dto.ReservationRequest;

import java.util.UUID;

public interface ReservationService {
    public Reservation createReservation(ReservationRequest reservation);
    public Reservation getReservationByToken(UUID token);
}
