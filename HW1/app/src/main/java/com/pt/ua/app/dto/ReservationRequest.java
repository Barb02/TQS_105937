package com.pt.ua.app.dto;

public class ReservationRequest {

    private Long tripId;
    private String name;
    private String address;
    private String phone;
    private String zipCode;
    private String cardNumber;
    private String cardExpirationMonth;
    private String cardExpirationYear;
    private String cardCvv;
    private String cardHolderName;

    public ReservationRequest() {
    }

    public ReservationRequest(Long tripId, String name, String address, String phone, String zipCode, String cardNumber, String cardExpirationMonth, String cardExpirationYear, String cardCvv, String cardHolderName) {
        this.tripId = tripId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.zipCode = zipCode;
        this.cardNumber = cardNumber;
        this.cardExpirationMonth = cardExpirationMonth;
        this.cardExpirationYear = cardExpirationYear;
        this.cardCvv = cardCvv;
        this.cardHolderName = cardHolderName;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
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

    
}
