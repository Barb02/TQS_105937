package com.pt.ua.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pt.ua.app.domain.Reservation;

import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID>{
    public Reservation findByToken(UUID token);
} 
