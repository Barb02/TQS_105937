package com.pt.ua.app.controller_thymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.pt.ua.app.service.TripService;

import org.springframework.ui.Model;

import com.pt.ua.app.domain.City;
import com.pt.ua.app.domain.Trip;
import com.pt.ua.app.dto.CityForm;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TripControllerT {

    private final TripService tripService;

    @Autowired
    public TripControllerT(TripService tripService) {
        this.tripService = tripService;
    }
    
    @GetMapping("/")
    public String getCities(Model model){
        model.addAttribute("cityForm", new CityForm());
        model.addAttribute("origins", tripService.getAllCities());
        return "index";
    }

    @PostMapping("/")
    public String submitForm(@ModelAttribute("cityForm") CityForm cityForm, Model model) {
        City selectedOrigin = cityForm.getSelectedOrigin();
        List<City> destinations = tripService.getDestinationCities(selectedOrigin);
        model.addAttribute("origins", tripService.getAllCities());
        model.addAttribute("destinations", destinations);
        return "index";
    }

    @GetMapping("/trips")
    public String getTrips(Long originId, Long destinationId, LocalDateTime startDateTime, LocalDateTime endDateTime, Model model){
        City origin = tripService.getCityById(originId);
        City destination = tripService.getCityById(destinationId);
        List<Trip> trips = tripService.getTrips(origin, destination, startDateTime, endDateTime);
        return "index";
    }


}
