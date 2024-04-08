package com.pt.ua.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pt.ua.app.domain.Trip;
import com.pt.ua.app.domain.City;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long>{
    @Query("SELECT t.city2 FROM Trip t WHERE t.city1 = :origin")
    List<City> findDestinationCities(@Param("origin") City origin);

    List<Trip> findByCity1AndCity2AndDateTimeBetweenAndSeatsGreaterThan(City origin, City destination, LocalDateTime startDate, LocalDateTime endDate, int seats);
}
