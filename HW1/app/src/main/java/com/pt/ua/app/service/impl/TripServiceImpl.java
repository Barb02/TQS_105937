package com.pt.ua.app.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pt.ua.app.repository.TripRepository;
import com.pt.ua.app.repository.CityRepository;
import com.pt.ua.app.service.TripService;
import com.pt.ua.app.domain.*;
import java.io.IOException;

import java.time.LocalDateTime;

import java.util.List;

@Service
public class TripServiceImpl implements TripService {
    
    private final TripRepository tripRepository;
    private final CityRepository cityRepository;
    private final ExchangeService exchangeService;

    private static final Logger log = LoggerFactory.getLogger(TripServiceImpl.class);

    public TripServiceImpl(TripRepository tripRepository, CityRepository cityRepository, ExchangeService exchangeService){
        this.tripRepository = tripRepository;
        this.cityRepository = cityRepository;
        this.exchangeService = exchangeService;
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
    public List<Trip> getTrips(City origin, City destination, LocalDateTime startDate, LocalDateTime endDate, String currency) throws IOException, InterruptedException{
        List<Trip> trips = tripRepository.findByCity1AndCity2AndDateTimeBetween(origin, destination, startDate, endDate);
        if(! currency.equals("EUR")){
            double currentExchangeRate = exchangeService.getExchangeRate(currency);
            for(Trip trip : trips){
                trip.setPrice(trip.getPrice() * currentExchangeRate);
            }
        }
        return trips;
    }

    @Override
    public City getCityById(Long id){
        return cityRepository.findById(id).orElse(null);
    }

    @Override
    public Trip getTripById(Long id){
        return tripRepository.findById(id).orElse(null);
    }
}
