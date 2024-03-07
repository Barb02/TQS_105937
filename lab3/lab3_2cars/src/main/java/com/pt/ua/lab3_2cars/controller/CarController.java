package com.pt.ua.lab3_2cars.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pt.ua.lab3_2cars.domain.Car;
import com.pt.ua.lab3_2cars.service.CarManagerService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {
    
    private final CarManagerService carManagerService;

    public CarController(CarManagerService carManagerService) {
        this.carManagerService = carManagerService;
    }

    @PostMapping("/cars")
    public ResponseEntity<Car> createCar(@RequestBody Car car){
        try{
            Car createdCar = carManagerService.save(car);
            return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/cars")
    public ResponseEntity<List<Car>> getAllCars(){
        List<Car> cars = carManagerService.getAllCars();
        if(cars != null){
            return new ResponseEntity<>(cars, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id){
        Car car = carManagerService.getCarDetails(id);
        if (car != null)
            return new ResponseEntity<>(car, HttpStatus.OK);
        else 
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
