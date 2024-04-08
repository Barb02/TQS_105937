package com.pt.ua.app.controller_thymeleaf;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(ReservationControllerT.class);

    @Autowired
    public ReservationControllerT(ReservationService reservationService, TripService tripService) {
        this.reservationService = reservationService;
        this.tripService = tripService;
    }
    
    @GetMapping("/trips/{tripId}/reservations")
    public String getReservationPage(@PathVariable long tripId, @RequestParam String currency, Model model){

        log.info("Getting reservation page for tripId: " + tripId + " and currency: " + currency);

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

        log.info("Creating reservation for trip: " + reservationRequest.getTripId());

        Reservation reservationSaved = reservationService.createReservation(reservationRequest);
        if(reservationSaved == null){
            model.addAttribute("error", "Reservation data is not valid");
            return "reservation";
        }
        return "redirect:/reservation-status?token=" + reservationSaved.getToken();
    }

    @GetMapping("/reservation-status")
    public String getReservationStatusPage(@RequestParam String token, Model model){

        log.info("Getting reservation status for token: " + token);

        UUID tokenUUID;
        try{
            tokenUUID = UUID.fromString(token);
        }
        catch(Exception e){
            log.info("Couldn't convert token to UUID");
            return "redirect:/token-error";
        }

        Reservation reservation = reservationService.getReservationByToken(tokenUUID);
        if (reservation == null){
            log.info("Couldn't find reservation");
            return "redirect:/token-error";
        }
        else{
            model.addAttribute("reservation", reservation);
            return "reservation-status";
        }
    }
    
}
