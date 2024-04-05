package com.pt.ua.app.service.impl;

import org.springframework.stereotype.Service;

import com.pt.ua.app.repository.TripRepository;
import com.pt.ua.app.repository.CityRepository;
import com.pt.ua.app.service.TripService;
import com.pt.ua.app.domain.*;

import java.time.LocalDateTime;

import java.util.List;

@Service
public class TripServiceImpl implements TripService {
    
    private final TripRepository tripRepository;
    private final CityRepository cityRepository;

    public TripServiceImpl(TripRepository tripRepository, CityRepository cityRepository) {
        this.tripRepository = tripRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public List<City> getAllCities(){
        return cityRepository.findAll();
    }

    @Override
    public List<City> getDestinationCities(City origin){
        return tripRepository.findDestinationCities(origin);
    }

    @Override
    public List<Trip> getTrips(City origin, City destination, LocalDateTime startDate, LocalDateTime endDate){
        return tripRepository.findByCity1AndCity2AndDateTimeBetween(origin, destination, startDate, endDate);
    }

    @Override
    public City getCityById(Long id){
        return cityRepository.findById(id).orElse(null);
    }
}
