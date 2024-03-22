package com.pt.ua.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.GetMapping;

import com.pt.ua.app.service.TripService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.pt.ua.app.domain.City;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TripController {

    private final TripService busService;

    @Autowired
    public TripController(TripService busService) {
        this.busService = busService;
    }
    
    @Operation(summary = "Get all available cities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = City.class)))}),
            @ApiResponse(responseCode = "404", description = "Cities not found", content = @Content)})
    @GetMapping("cities")
    public List<City> getCities(){
        List<City> cities = busService.getAllCities();
        if(cities != null)
            return cities;
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cities not found");
        }   
    }
     

}
