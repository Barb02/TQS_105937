package com.pt.ua.lab7_4carsIT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pt.ua.lab7_4carsIT.domain.Car;
import com.pt.ua.lab7_4carsIT.repository.CarRepository;
import com.pt.ua.lab7_4carsIT.service.CarManagerServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {
    
    @Mock(lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerServiceImpl carManagerService;

    @BeforeEach
    public void setUp(){
        Car gladiator = new Car("Jeep", "Gladiator");
        Car renegade = new Car("Jeep", "Renegade");
        Car celta = new Car("Chevrolet", "Celta");
        celta.setCarId(1L);

        List<Car> allCars = Arrays.asList(gladiator, renegade, celta);
  
        Mockito.when(carRepository.findByCarId(celta.getCarId())).thenReturn(celta);
        Mockito.when(carRepository.findAll()).thenReturn(allCars);
        Mockito.when(carRepository.findByCarId(-99L)).thenReturn(null);
    }

    private void verifyFindByCarIdIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findByCarId(Mockito.anyLong());
    }

    private void verifyFindAllCarIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findAll();
    }

    private void verifySaveIsCalledOnce() {
        Mockito.verify(carRepository, VerificationModeFactory.times(1)).save(Mockito.any());
    }

    @Test
    void whenCarCreated_thenCarShouldBeReturned(){
        Car gladiator = new Car("Jeep", "Gladiator");
        Mockito.when(carRepository.save(gladiator)).thenReturn(gladiator);
        Car savedCar = carManagerService.save(gladiator);
        assertThat(savedCar).isEqualTo(gladiator);
        verifySaveIsCalledOnce();
    }

    @Test
    void whenGetAllCars_thenAllCarsShouldBeReturned(){
        List<Car> allCars = carManagerService.getAllCars();
        assertThat(allCars).hasSize(3).extracting(Car::getModel).contains("Gladiator", "Renegade", "Celta");
        verifyFindAllCarIsCalledOnce();
    }

    @Test
    void whenGetCarDetails_thenDetailsShouldBeReturned(){
        Car car = carManagerService.getCarDetails(1L);
        assertThat(car.getModel()).isEqualTo("Celta");
        verifyFindByCarIdIsCalledOnce();
    }

    @Test
    void whenInvalidId_thenNoCarSouldBeReturned(){
        Car response = carManagerService.getCarDetails(-99L);
        assertThat(response).isNull();
        verifyFindByCarIdIsCalledOnce();
    }
}
