package com.pt.ua.app.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Set;
import java.time.LocalDateTime;

@Entity
@Table(name = "connection")
public class Connection {

    @ManyToOne
    @JoinColumn(name = "city1_id", referencedColumnName = "id")
    private City city1;

    @ManyToOne
    @JoinColumn(name = "city2_id", referencedColumnName = "id")
    private City city2;

    @Column
    private double value;

    @Column
    private Set<LocalDateTime> dates;

    public Connection(City city1, City city2, double value) {
        this.city1 = city1;
        this.city2 = city2;
        this.value = value;
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

    public Set<LocalDateTime> getDates() {
        return dates;
    }

    public void setDates(Set<LocalDateTime> dates) {
        this.dates = dates;
    }
    
}
