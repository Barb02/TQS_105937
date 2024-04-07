package com.pt.ua.app.dto;

import java.time.LocalDateTime;

public class TripSearch {
    Long city1Id;
    Long city2Id;
    LocalDateTime startDateTime;
    LocalDateTime endDateTime;
    String selectedCurrency;

    public TripSearch(){
        
    }

    public TripSearch (Long city1Id, Long city2Id, LocalDateTime startDateTime, LocalDateTime endDateTime, String selectedCurrency){
        this.city1Id = city1Id;
        this.city2Id = city2Id;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.selectedCurrency = selectedCurrency;
    }

    public Long getCity1Id() {
        return city1Id;
    }
    public void setCity1Id(Long city1Id) {
        this.city1Id = city1Id;
    }
    public Long getCity2Id() {
        return city2Id;
    }
    public void setCity2Id(Long city2Id) {
        this.city2Id = city2Id;
    }
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getSelectedCurrency() {
        return selectedCurrency;
    }

    public void setSelectedCurrency(String selectedCurrency) {
        this.selectedCurrency = selectedCurrency;
    }
    

    
}
