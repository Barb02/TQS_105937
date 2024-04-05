package com.pt.ua.lab7_2cars.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pt.ua.lab7_2cars.domain.Car;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long>{
    
    public Car findByCarId(Long carId);
    public List<Car> findAll();
} 
