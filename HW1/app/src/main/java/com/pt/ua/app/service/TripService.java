package com.pt.ua.app.service;

import java.util.List;
import java.time.LocalDate;

import com.pt.ua.app.domain.City;

public interface TripService {
    public List<City> getAllCities();
    public List<City> getDestinationCities(City origin, LocalDate date);
} 
