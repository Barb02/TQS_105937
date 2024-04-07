package com.pt.ua.app.controller_thymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

import com.pt.ua.app.service.TripService;
import com.pt.ua.app.dto.TripSearch;
import com.pt.ua.app.domain.City;
import com.pt.ua.app.domain.Trip;
import com.pt.ua.app.dto.CityForm;

import java.io.IOException;
import java.util.ArrayList;
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
        model.addAttribute("tripSearch", new TripSearch());
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

    @PostMapping("/trips")
    public String getTrips(@ModelAttribute("cityForm") CityForm cityForm, TripSearch search, Model model){
        City origin = tripService.getCityById(search.getCity1Id());
        City destination = tripService.getCityById(search.getCity2Id());
        
        List<Trip> trips;
        try{
            trips = tripService.getTrips(origin, destination, search.getStartDateTime(), search.getEndDateTime(), search.getSelectedCurrency());
        }
        catch(IOException | InterruptedException e){
            model.addAttribute("error", "Error fetching exchange rates from external service");
            return "index";
        }
        
        model.addAttribute("trips", trips);
        
        model.addAttribute("origins", tripService.getAllCities());

        List<City> destinations = tripService.getDestinationCities(origin);
        model.addAttribute("destinations", destinations);

        return "index";
    }


}
