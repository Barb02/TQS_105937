package com.pt.ua.lab3_2cars.service;

import com.pt.ua.lab3_2cars.domain.Car;

import java.util.List;

public interface CarManagerService {
    public Car save(Car car);
    public List<Car> getAllCars();
    public Car getCarDetails(Long id);
} 
