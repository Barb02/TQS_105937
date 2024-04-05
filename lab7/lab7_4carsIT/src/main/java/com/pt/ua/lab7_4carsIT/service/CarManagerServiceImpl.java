package com.pt.ua.lab7_4carsIT.service;

import org.springframework.stereotype.Service;

import com.pt.ua.lab7_4carsIT.domain.Car;
import com.pt.ua.lab7_4carsIT.repository.CarRepository;

import java.util.List;

@Service
public class CarManagerServiceImpl implements CarManagerService{
    
    private final CarRepository carRepository;

    public CarManagerServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Car getCarDetails(Long id) {
        return carRepository.findByCarId(id);
    }
}
