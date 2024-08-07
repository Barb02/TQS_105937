package com.pt.ua.app.controller_thymeleaf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.List;

@Controller
public class TripControllerT {

    private static final Logger log = LoggerFactory.getLogger(TripControllerT.class);

    private final TripService tripService;
    private final String indexPage = "index";
    private final String originsAttribute = "origins";

    @Autowired
    public TripControllerT(TripService tripService) {
        this.tripService = tripService;
    }
    
    @GetMapping("/")
    public String getCities(Model model){

        log.info("Getting index page with origin cities");

        model.addAttribute("cityForm", new CityForm());
        model.addAttribute(originsAttribute, tripService.getAllCities());
        model.addAttribute("tripSearch", new TripSearch());
        return indexPage;
    }

    @GetMapping("/token-error")
    public String getCitiesTokenError(Model model){

        log.info("Getting index page with token error message");

        model.addAttribute("cityForm", new CityForm());
        model.addAttribute(originsAttribute, tripService.getAllCities());
        model.addAttribute("tripSearch", new TripSearch());
        model.addAttribute("errorToken", "Invalid token!");
        return indexPage;
    }

    @PostMapping("/")
    public String submitForm(@ModelAttribute("cityForm") CityForm cityForm, Model model) {

        log.info("Submitting origin cities form with origin city: " + cityForm.getSelectedOrigin().getName());

        City selectedOrigin = cityForm.getSelectedOrigin();
        List<City> destinations = tripService.getDestinationCities(selectedOrigin);
        model.addAttribute(originsAttribute, tripService.getAllCities());
        model.addAttribute("destinations", destinations);
        return indexPage;
    }

    @PostMapping("/trips")
    public String getTrips(@ModelAttribute("cityForm") CityForm cityForm, TripSearch search, Model model){
        
        City origin = tripService.getCityById(search.getCity1Id());
        City destination = tripService.getCityById(search.getCity2Id());

        log.info("Getting trips for origin city: " + origin.getName() + " and destination city: " + destination.getName() + " from " + search.getStartDateTime() + " to " + search.getEndDateTime() + " in currency: " + search.getSelectedCurrency());
        
        List<Trip> trips;
        try{
            trips = tripService.getTrips(origin, destination, search.getStartDateTime(), search.getEndDateTime(), search.getSelectedCurrency());
        }
        catch(IOException | InterruptedException e){
            model.addAttribute("error", "Error fetching exchange rates from external service");
            return indexPage;
        }
        
        model.addAttribute("trips", trips);
        
        model.addAttribute(originsAttribute, tripService.getAllCities());

        List<City> destinations = tripService.getDestinationCities(origin);
        model.addAttribute("destinations", destinations);

        return indexPage;
    }


}
