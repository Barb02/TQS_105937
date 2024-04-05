package com.pt.ua.app.service;

import java.util.List;
import java.time.LocalDateTime;

import com.pt.ua.app.domain.City;
import com.pt.ua.app.domain.Trip;

public interface TripService {
    public City getCityById(Long id);
    public List<City> getAllCities();
    public List<City> getDestinationCities(City origin);
    public List<Trip> getTrips(City origin, City destination, LocalDateTime startDate, LocalDateTime endDate);
} 