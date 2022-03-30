package pt.ua.deti.tqs.cars_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import pt.ua.deti.tqs.cars_service.data.Car;
import pt.ua.deti.tqs.cars_service.data.CarRepository;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CarRepositoryTest {

    @Autowired
    private TestEntityManager manager;

    @Autowired
    private CarRepository repository;

    @Test
    void findByCarId() {

        Car car = new Car("Nissan", "Micra");
        manager.persistAndFlush(car);

        Car result = repository.findByCarId(car.getCarId());
        assertThat(result).isEqualTo(car);
    }

    @Test
    void findByCarIdNonExistent() {
        Car result = repository.findByCarId(-99L);
        assertThat(result).isNull();
    }

    @Test
    void findAll() {

        Car car1 = new Car("Nissan", "Micra");
        Car car2 = new Car("Ferrari", "Roma");
        Car car3 = new Car("Opel", "Manta 400");

        List<Car> allCars = Arrays.asList(car1, car2, car3);

        for (Car car: allCars) {
            manager.persist(car);
        }
        manager.flush();

        allCars = repository.findAll();

        assertThat(allCars).hasSize(3).extracting(Car::getCarId).containsOnly(car1.getCarId(), car2.getCarId(), car3.getCarId());
        assertThat(allCars).hasSize(3).extracting(Car::getMaker).containsOnly(car1.getMaker(), car2.getMaker(), car3.getMaker());
        assertThat(allCars).hasSize(3).extracting(Car::getModel).containsOnly(car1.getModel(), car2.getModel(), car3.getModel());
    }
}