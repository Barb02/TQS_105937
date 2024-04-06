package com.pt.ua.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.pt.ua.app.service.ReservationService;
import com.pt.ua.app.domain.Reservation;
import com.pt.ua.app.dto.ReservationRequest;


@RestController
@RequestMapping("/api/v1")
public class ReservationController {
    
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "Post a reservation for a trip")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Reservation.class))}),
            @ApiResponse(responseCode = "400", description = "Reservation data is not valid", content = @Content)})
    @PostMapping("reservations")
    public Reservation createReservation(@RequestBody ReservationRequest reservationRequest){
        Reservation reservationSaved = reservationService.createReservation(reservationRequest);
        if (reservationSaved != null)
            return reservationSaved;
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation data is not valid");
        }
    }
}
