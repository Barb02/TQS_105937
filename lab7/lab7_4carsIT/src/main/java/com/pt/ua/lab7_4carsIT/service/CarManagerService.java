package com.pt.ua.lab7_4carsIT.service;

import java.util.List;

import com.pt.ua.lab7_4carsIT.domain.Car;

public interface CarManagerService {
    public Car save(Car car);
    public List<Car> getAllCars();
    public Car getCarDetails(Long id);
} 
