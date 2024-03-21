package com.pt.ua.app.service;

import java.util.List;
import java.time.LocalDate;

import com.pt.ua.app.domain.City;

public interface BusService {
    //public Reservation createReservation(ReservationDTO reservationData);
    public List<City> getAllCities();
    public List<City> getDestinationCities(String origin, LocalDate date);
} 
