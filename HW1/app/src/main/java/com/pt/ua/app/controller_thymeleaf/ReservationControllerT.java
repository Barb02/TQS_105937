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
import com.pt.ua.app.domain.Trip;
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
    public String getReservationPage(@PathVariable long tripId, @RequestParam String currency, Model model){
        Trip trip = null;
        try{
            trip = tripService.getTripById(tripId, currency);
        }
        catch(Exception e){
            model.addAttribute("error", "Error fetching trip details");
            return "reservation";
        }
        model.addAttribute("trip", trip);
        return "reservation";
    }

    @PostMapping("/trips/{tripId}/reservations")
    public String createReservation(@ModelAttribute ReservationRequest reservationRequest, Model model){
        Reservation reservationSaved = reservationService.createReservation(reservationRequest);
        if(reservationSaved == null){
            model.addAttribute("error", "Reservation data is not valid");
            return "reservation";
        }
        return "redirect:/reservation-status?token=" + reservationSaved.getId();
    }

    @GetMapping("/reservation-status")
    public String getReservationStatusPage(@RequestParam String token, Model model){
        UUID reservationId;
        try{
            reservationId = UUID.fromString(token);
        }
        catch(Exception e){
            return "redirect:/token-error";
        }

        Reservation reservation = reservationService.getReservationById(reservationId);
        if (reservation == null){
            return "redirect:/token-error";
        }
        else{
            model.addAttribute("reservation", reservation);
            return "reservation-status";
        }
    }
    
}
