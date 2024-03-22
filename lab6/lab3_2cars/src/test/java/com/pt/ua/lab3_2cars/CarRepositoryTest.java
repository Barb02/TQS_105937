package com.pt.ua.lab3_2cars;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.pt.ua.lab3_2cars.repository.CarRepository;
import com.pt.ua.lab3_2cars.domain.Car;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
public class CarRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Test
    void whenFindByValidCarId_thenReturnCar(){
        Car celta = new Car("Chevrolet", "Celta");
        entityManager.persistAndFlush(celta);

        Car fromDb = carRepository.findByCarId(celta.getCarId());
        assertThat(fromDb).isEqualTo(celta);
    }

    @Test
    void whenFindByInvalidCarId_thenReturnNull(){
        Car fromDb = carRepository.findByCarId(-99L);
        assertThat(fromDb).isNull();
    }

    @Test
    void givenListOfCars_whenFindAll_thenReturnAllCars(){
        Car jeep = new Car("Jeep", "Gladiator");
        Car uno = new Car("Fiat", "Uno");
        Car celta = new Car("Chevrolet", "Celta");

        entityManager.persist(jeep);
        entityManager.persist(uno);
        entityManager.persist(celta);

        entityManager.flush();

        List<Car> dbCars = carRepository.findAll();

        assertThat(dbCars).hasSize(3).extracting(Car::getModel).containsOnly(jeep.getModel(), uno.getModel(), celta.getModel());
    } 

}
