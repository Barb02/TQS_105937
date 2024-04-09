package com.pt.ua.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.pt.ua.app.domain.City;
import com.pt.ua.app.domain.Trip;
import com.pt.ua.app.repository.TripRepository;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TripRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TripRepository tripRepository;

    private City aveiro;
    private City porto;
    private City lisboa;
    private Trip aveiroPorto1;
    private Trip aveiroPorto2;
    private Trip aveiroLisboa;
    private Trip portoLisboa;

    @BeforeEach
    void setUp() throws Exception {
        aveiro = new City("Aveiro");
        porto = new City("Porto");
        lisboa = new City("Lisboa");

        LocalDateTime time1 = LocalDateTime.of(2024, 04, 05, 10, 00);
        LocalDateTime time2 = LocalDateTime.of(2024, 04, 05, 11, 00);
        LocalDateTime time3 = LocalDateTime.of(2024, 04, 05, 12, 00);

        aveiroPorto1 = new Trip(aveiro, porto, 10, time1,20);
        aveiroPorto2 = new Trip(aveiro, porto, 10, time2, 20);
        aveiroLisboa = new Trip(aveiro, lisboa, 15, time3, 25);
        portoLisboa = new Trip(porto, lisboa, 15, time2, 0);

        entityManager.persist(aveiro);
        entityManager.persist(porto);
        entityManager.persist(lisboa);
        entityManager.persist(aveiroPorto1);
        entityManager.persist(aveiroPorto2);
        entityManager.persist(aveiroLisboa);
        entityManager.persist(portoLisboa);

        entityManager.flush();
    }

    @Test
    void givenCityWithAvailableDestinations_whenFindDestinationCities_thenReturnDestinationCities(){
        List<City> response = tripRepository.findDestinationCities(aveiro);
        List<City> aveiroDestinationCities = Arrays.asList(porto, lisboa);
        assertThat(response).isEqualTo(aveiroDestinationCities);
    }

    @Test
    void givenCityWithoutAvailableDestinations_whenFindDestinationCities_thenReturnDestinationCities(){
        List<City> response = tripRepository.findDestinationCities(lisboa);
        assertThat(response).isEqualTo(new ArrayList<>());
    }

    @Test
    void whenFindTripsForCitiesAndDate_thenReturnValidTrips(){
        LocalDateTime time1 = LocalDateTime.of(2024, 04, 05, 10, 00);
        LocalDateTime time2 = LocalDateTime.of(2024, 04, 05, 11, 00);
        List<Trip> response = tripRepository.findByCity1AndCity2AndDateTimeBetweenAndSeatsGreaterThan(aveiro,porto,time1,time2,0);
        List<Trip> validTrips = Arrays.asList(aveiroPorto1, aveiroPorto2);
        assertThat(response).isEqualTo(validTrips);
    }

    @Test
    void whenFindTripsForCitiesAndDate_thenReturnValidTrips2(){
        LocalDateTime time1 = LocalDateTime.of(2024, 04, 05, 9, 00);
        LocalDateTime time2 = LocalDateTime.of(2024, 04, 05, 10, 30);
        List<Trip> response = tripRepository.findByCity1AndCity2AndDateTimeBetweenAndSeatsGreaterThan(aveiro,porto,time1,time2,0);
        List<Trip> validTrips = Arrays.asList(aveiroPorto1);
        assertThat(response).isEqualTo(validTrips);
    }

    @Test
    void givenFullTrips_whenFindTrips_thenReturnNoTrips(){
        LocalDateTime time1 = LocalDateTime.of(2024, 04, 05, 10, 00);
        LocalDateTime time2 = LocalDateTime.of(2024, 04, 05, 12, 00);
        List<Trip> response = tripRepository.findByCity1AndCity2AndDateTimeBetweenAndSeatsGreaterThan(porto,lisboa,time1,time2,0);
        assertThat(response).isEqualTo(new ArrayList<>());
    }
}
