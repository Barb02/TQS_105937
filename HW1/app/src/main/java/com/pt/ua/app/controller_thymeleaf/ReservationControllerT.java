package com.pt.ua.app.controller_thymeleaf;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.pt.ua.app.service.ReservationService;
import com.pt.ua.app.service.TripService;
import com.pt.ua.app.domain.Reservation;
import com.pt.ua.app.dto.ReservationRequest;

@Controller
public class ReservationControllerT {
    
    private final ReservationService reservationService;
    private final TripService tripService;

    @Autowired
    public ReservationControllerT(ReservationService reservationService, TripService tripService) {
        this.reservationService = reservationService;
        this.tripService = tripService;
    }
    
    @GetMapping("/trips/{tripId}/reservations")
    public String getReservationPage(@PathVariable long tripId, Model model){
        model.addAttribute("trip", tripService.getTripById(tripId));
        return "reservation";
    }

    @PostMapping("/trips/{tripId}/reservations")
    public String createReservation(@ModelAttribute ReservationRequest reservationRequest, Model model){
        Reservation reservationSaved = reservationService.createReservation(reservationRequest);
        return "redirect:/reservation-status?token=" + reservationSaved.getId();
    }

    @GetMapping("/reservation-status")
    public String getReservationStatusPage(@RequestParam String token, Model model){
        UUID reservationId = UUID.fromString(token);
        model.addAttribute("reservation", reservationService.getReservationById(reservationId));
        return "reservation-status";
    }
    
}
