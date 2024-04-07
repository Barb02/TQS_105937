package com.pt.ua.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pt.ua.app.service.TripService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.pt.ua.app.domain.City;
import com.pt.ua.app.domain.Trip;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TripController {

    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }
    
    @Operation(summary = "Get all available cities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = City.class)))}),
            @ApiResponse(responseCode = "404", description = "Cities not found", content = @Content)})
    @GetMapping("cities")
    public List<City> getCities(){
        List<City> cities = tripService.getAllCities();
        if(cities != null)
            return cities;
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cities not found");
        }   
    }
     
    @Operation(summary = "Get all destination cities from a given origin city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = City.class)))}),
            @ApiResponse(responseCode = "404", description = "Cities not found", content = @Content)})
    @GetMapping("cities/{originId}/destinations")
    public List<City> getDestinationCities(@PathVariable Long originId){
        City origin = tripService.getCityById(originId);
        List<City> cities = tripService.getDestinationCities(origin);
        if(cities != null)
            return cities;
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cities not found");
        }   
    }

    @Operation(summary = "Get all available trips")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Trip.class)))}),
            @ApiResponse(responseCode = "404", description = "Trips not found", content = @Content)})
    @GetMapping("trips")
    public List<Trip> getTrips(@RequestParam Long originId, @RequestParam Long destinationId, @RequestParam String startDate, @RequestParam String endDate, @RequestParam String currency){
        City origin = tripService.getCityById(originId);
        City destination = tripService.getCityById(destinationId);
        LocalDateTime startDateTime = LocalDateTime.parse(startDate);
        LocalDateTime endDateTime = LocalDateTime.parse(endDate);
        List<Trip> trips; 
        try{
            trips = tripService.getTrips(origin, destination, startDateTime, endDateTime, currency);
        }
        catch(IOException | InterruptedException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching exchange rates from external service");
        }
        if(trips != null)
            return trips;
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trips not found");
        }   
    }

}
