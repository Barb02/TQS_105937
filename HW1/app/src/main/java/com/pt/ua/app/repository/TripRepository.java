package com.pt.ua.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pt.ua.app.domain.Trip;
import com.pt.ua.app.domain.City;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long>{
    public List<Trip> findByCity1AndDateBetween(City origin, LocalDateTime startDate, LocalDateTime endDate);
}
