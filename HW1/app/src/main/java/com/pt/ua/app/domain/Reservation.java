package com.pt.ua.app.domain;

import java.util.UUID;

import com.pt.ua.app.dto.ReservationRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "reservation")
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private UUID token;

    @ManyToOne
    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    private Trip trip;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String phone;

    @Column
    private String zipCode;

    @Column
    private String cardNumber;

    @Column
    private String cardExpirationMonth;

    @Column
    private String cardExpirationYear;

    @Column
    private String cardCvv;

    @Column
    private String cardHolderName;

    @Column
    private ReservationStatus status;

    @Column
    private int numberOfTickets;

    public Reservation() {
    }

    public Reservation(ReservationRequest reservationRequest) {
        this.token = UUID.randomUUID();
        this.name = reservationRequest.getName();
        this.address = reservationRequest.getAddress();
        this.phone = reservationRequest.getPhone();
        this.zipCode = reservationRequest.getZipCode();
        this.cardNumber = reservationRequest.getCardNumber();
        this.cardExpirationMonth = reservationRequest.getCardExpirationMonth();
        this.cardExpirationYear = reservationRequest.getCardExpirationYear();
        this.cardCvv = reservationRequest.getCardCvv();
        this.cardHolderName = reservationRequest.getCardHolderName();
        this.status = ReservationStatus.CONFIRMED;
        this.numberOfTickets = reservationRequest.getNumberOfTickets();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Trip getTrip() {
        return trip;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardExpirationMonth() {
        return cardExpirationMonth;
    }

    public void setCardExpirationMonth(String cardExpirationMonth) {
        this.cardExpirationMonth = cardExpirationMonth;
    }

    public String getCardExpirationYear() {
        return cardExpirationYear;
    }

    public void setCardExpirationYear(String cardExpirationYear) {
        this.cardExpirationYear = cardExpirationYear;
    }

    public String getCardCvv() {
        return cardCvv;
    }

    public void setCardCvv(String cardCvv) {
        this.cardCvv = cardCvv;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }
    
}
