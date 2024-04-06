package com.pt.ua.app;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.pt.ua.app.domain.City;
import com.pt.ua.app.domain.Trip;
import com.pt.ua.app.repository.CityRepository;
import com.pt.ua.app.repository.TripRepository;

import java.util.Arrays;
import java.util.List;

@Component
@Profile("!test")
public class LoadDB implements CommandLineRunner {
    private final TripRepository tripRepository;
	private final CityRepository cityRepository;

	@Autowired
	public LoadDB(TripRepository tripRepository, CityRepository cityRepository) {
		this.tripRepository = tripRepository;
		this.cityRepository = cityRepository;
	}

    @Override
    public void run(String... args) throws Exception {
        if(cityRepository.findAll().isEmpty() && tripRepository.findAll().isEmpty())
            loadDB();
    }

    public void loadDB() {
		City aveiro = new City("Aveiro");
        City porto = new City("Porto");
        City lisboa = new City("Lisboa");
		List<City> allCities = Arrays.asList(aveiro, porto, lisboa);

        LocalDateTime time1 = LocalDateTime.of(2024, 04, 05, 10, 00);
        LocalDateTime time2 = LocalDateTime.of(2024, 04, 05, 11, 00);
        LocalDateTime time3 = LocalDateTime.of(2024, 04, 05, 12, 00);

        Trip aveiroPorto1 = new Trip(aveiro, porto, 10, time1, 20);
        Trip aveiroPorto2 = new Trip(aveiro, porto, 10, time2, 20);
        Trip aveiroLisboa = new Trip(aveiro, lisboa, 15, time3, 25);

		List<Trip> allTrips = Arrays.asList(aveiroPorto1, aveiroPorto2, aveiroLisboa);

		for (City city : allCities) {
			cityRepository.save(city);
		}

		for (Trip trip : allTrips) {
			tripRepository.save(trip);
		}
	}
}
