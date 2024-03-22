package com.pt.ua.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pt.ua.app.domain.City;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{
    public List<City> findAll();
}
