package com.pt.ua.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.pt.ua.app.service.ReservationService;
import com.pt.ua.app.domain.Reservation;
import com.pt.ua.app.dto.ReservationRequest;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ReservationController {

    private static final Logger log = LoggerFactory.getLogger(ReservationController.class);
    
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "Post a reservation for a trip")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Reservation.class))}),
            @ApiResponse(responseCode = "400", description = "Reservation data is not valid", content = @Content)})
    @PostMapping(value="reservations")
    @ResponseStatus(code = HttpStatus.CREATED, reason = "Created")
    public Reservation createReservation(@RequestBody ReservationRequest reservationRequest){

        log.info("Creating reservation for trip: " + reservationRequest.getTripId());
        
        Reservation reservationSaved = reservationService.createReservation(reservationRequest);
        if (reservationSaved != null){
            return reservationSaved;
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reservation data is not valid");
        }
    }

    @Operation(summary = "Get a reservation by its token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Reservation.class))}),
            @ApiResponse(responseCode = "404", description = "Reservation not found", content = @Content)})
    @GetMapping("reservations/{tokenUUID}")
    public Reservation getReservationById(@PathVariable UUID tokenUUID){

        log.info("Getting reservation by token " + tokenUUID);

        Reservation reservation = reservationService.getReservationByToken(tokenUUID);
        if (reservation != null)
            return reservation;
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found");
        }
    }
}
