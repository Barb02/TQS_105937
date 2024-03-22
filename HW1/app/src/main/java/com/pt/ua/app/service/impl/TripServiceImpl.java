package com.pt.ua.app.service.impl;

import org.springframework.stereotype.Service;

import com.pt.ua.app.repository.TripRepository;
import com.pt.ua.app.repository.CityRepository;
import com.pt.ua.app.service.TripService;
import com.pt.ua.app.domain.*;

import java.util.List;

@Service
public class TripServiceImpl implements TripService {
    
    private final TripRepository tripRepository;
    private final CityRepository cityRepository;

    public TripServiceImpl(TripRepository tripRepository, CityRepository cityRepository) {
        this.tripRepository = tripRepository;
        this.cityRepository = cityRepository;
    }

    public List<City> getAllCities(){
        return cityRepository.findAll();
    }

    public List<City> getDestinationCities(Long originId){
        return cityRepository.findDestinationCities();
    }
}
