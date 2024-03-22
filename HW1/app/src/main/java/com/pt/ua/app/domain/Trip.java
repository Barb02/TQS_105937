package com.pt.ua.app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "city1_id", referencedColumnName = "id")
    private City city1;

    @ManyToOne
    @JoinColumn(name = "city2_id", referencedColumnName = "id")
    private City city2;

    @Column
    private double value;

    @Column
    private LocalDateTime dateTime;

    public Trip(City city1, City city2, double value, LocalDateTime dateTime) {
        this.city1 = city1;
        this.city2 = city2;
        this.value = value;
        this.dateTime = dateTime;
    }

    public City getCity1() {
        return city1;
    }

    public void setCity1(City city1) {
        this.city1 = city1;
    }

    public City getCity2() {
        return city2;
    }

    public void setCity2(City city2) {
        this.city2 = city2;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDateTime getDates() {
        return dateTime;
    }

    public void setDates(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    
}
