package com.pt.ua.lab7_4carsIT;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.pt.ua.lab7_4carsIT.domain.Car;
import com.pt.ua.lab7_4carsIT.repository.CarRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class CarAPITestIT {
    
    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    private Long createTestCar(String maker, String model) {
        Car car = new Car(maker, model);
        Car savedCar = repository.saveAndFlush(car);
        return savedCar.getCarId();
    }

    @Test
    void whenValidInput_thenCreateCar(){
        Car renegade = new Car("Jeep", "Renegade");
        ResponseEntity<Car> created = restTemplate.postForEntity("/api/cars", renegade, Car.class);

        Car fromDb = repository.findByCarId(created.getBody().getCarId());
        assertThat(fromDb.getMaker()).isEqualTo("Jeep");

        List<Car> found = repository.findAll();
        assertThat(found).extracting(Car::getModel).containsOnly("Renegade");
    }

    @Test
    void givenCars_whenGetCars_thenStatus200()  {
        createTestCar("Chrevolet", "Celta");
        createTestCar("Jeep", "Gladiator");


        ResponseEntity<List<Car>> response = restTemplate
                .exchange("/api/cars", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getModel).containsExactly("Celta", "Gladiator");
    }

    @Test
    void givenCars_whenGetByCarId_thenStatus200() {
        createTestCar("Jeep", "Gladiator");
        Long ch2 = createTestCar("Chrevolet", "Celta");

        ResponseEntity<Car> response = restTemplate.getForEntity("/api/cars/" + ch2, Car.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMaker()).isEqualTo("Chrevolet");
    }

    @Test
    void givenCars_whenGetByInvalidCarId_thenStatus404() {
        createTestCar("Chrevolet", "Celta");

        ResponseEntity<Car> response = restTemplate.getForEntity("/api/cars/2", Car.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
