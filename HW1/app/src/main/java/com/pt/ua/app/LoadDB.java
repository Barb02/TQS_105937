package com.pt.ua.app;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.pt.ua.app.domain.City;
import com.pt.ua.app.domain.Reservation;
import com.pt.ua.app.domain.Trip;
import com.pt.ua.app.dto.ReservationRequest;
import com.pt.ua.app.repository.CityRepository;
import com.pt.ua.app.repository.ReservationRepository;
import com.pt.ua.app.repository.TripRepository;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
@Profile("!test")
public class LoadDB implements CommandLineRunner {
    private final TripRepository tripRepository;
	private final CityRepository cityRepository;
	private final ReservationRepository reservationRepository;

	@Autowired
	public LoadDB(TripRepository tripRepository, CityRepository cityRepository, ReservationRepository reservationRepository) {
		this.tripRepository = tripRepository;
		this.cityRepository = cityRepository;
		this.reservationRepository = reservationRepository;
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
		City savedAveiro = cityRepository.save(aveiro);
		City savedPorto = cityRepository.save(porto);
		cityRepository.save(lisboa);

        LocalDateTime time1 = LocalDateTime.of(2024, 04, 05, 10, 00);
        LocalDateTime time2 = LocalDateTime.of(2024, 04, 05, 11, 00);
        LocalDateTime time3 = LocalDateTime.of(2024, 04, 05, 12, 00);
		LocalDateTime time4 = LocalDateTime.of(2024, 04, 13, 10, 00);
		LocalDateTime time5 = LocalDateTime.of(2024, 04, 13, 15, 00);

        Trip aveiroPorto1 = new Trip(savedAveiro, savedPorto, 10, time1, 20);
        Trip aveiroPorto2 = new Trip(aveiro, porto, 10, time2, 20);
        Trip aveiroLisboa = new Trip(aveiro, lisboa, 15, time3, 25);
		Trip aveiroPorto3 = new Trip(aveiro, porto, 12, time4, 20);
        Trip aveiroPorto4 = new Trip(aveiro, porto, 10, time5, 20);

		List<Trip> allTrips = Arrays.asList(aveiroPorto2, aveiroLisboa, aveiroPorto3, aveiroPorto4);

		for (Trip trip : allTrips) {
			tripRepository.save(trip);
		}

		Trip tripSaved = tripRepository.save(aveiroPorto1);
		Reservation reservation = new Reservation(new ReservationRequest(tripSaved.getId(), "João", "Rua do João", "912345678", "3800-000", "1234567890123456", "12", "2024", "123", "João", 2));
		reservation.setToken(UUID.fromString("c1c4577b-0c71-40f6-b42a-d30461792708"));
		reservation.setTrip(tripSaved);
		Reservation savedReservation = reservationRepository.save(reservation);
	}
}
