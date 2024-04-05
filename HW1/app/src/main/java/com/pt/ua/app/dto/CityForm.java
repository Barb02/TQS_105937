package com.pt.ua.app.dto;

import com.pt.ua.app.domain.City;

public class CityForm {
    private City selectedOrigin;
    private City selectedDestination;

    public City getSelectedOrigin() {
        return selectedOrigin;
    }

    public void setSelectedOrigin(City selectedOrigin) {
        this.selectedOrigin = selectedOrigin;
    }

    public City getSelectedDestination() {
        return selectedDestination;
    }

    public void setSelectedDestination(City selectedDestination) {
        this.selectedDestination = selectedDestination;
    }
}

