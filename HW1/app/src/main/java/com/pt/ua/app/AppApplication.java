package com.pt.ua.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;

import com.pt.ua.app.domain.City;
import com.pt.ua.app.domain.Trip;
import com.pt.ua.app.repository.CityRepository;
import com.pt.ua.app.repository.TripRepository;

@SpringBootApplication
public class AppApplication {

	private final TripRepository tripRepository;
	private final CityRepository cityRepository;

	@Autowired
	public AppApplication(TripRepository tripRepository, CityRepository cityRepository) {
		this.tripRepository = tripRepository;
		this.cityRepository = cityRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
    public void loadDB() {
		City aveiro = new City("Aveiro");
        City porto = new City("Porto");
        City lisboa = new City("Lisboa");
		List<City> allCities = Arrays.asList(aveiro, porto, lisboa);

        LocalDateTime time1 = LocalDateTime.of(2024, 04, 05, 10, 00);
        LocalDateTime time2 = LocalDateTime.of(2024, 04, 05, 11, 00);
        LocalDateTime time3 = LocalDateTime.of(2024, 04, 05, 12, 00);

        Trip aveiroPorto1 = new Trip(aveiro, porto, 10, time1);
        Trip aveiroPorto2 = new Trip(aveiro, porto, 10, time2);
        Trip aveiroLisboa = new Trip(aveiro, lisboa, 15, time3);

		List<Trip> allTrips = Arrays.asList(aveiroPorto1, aveiroPorto2, aveiroLisboa);

		for (City city : allCities) {
			cityRepository.save(city);
		}

		for (Trip trip : allTrips) {
			tripRepository.save(trip);
		}
	}

}
