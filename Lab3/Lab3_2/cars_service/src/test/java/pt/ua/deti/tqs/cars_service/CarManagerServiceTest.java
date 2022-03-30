package pt.ua.deti.tqs.cars_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.ua.deti.tqs.cars_service.data.Car;
import pt.ua.deti.tqs.cars_service.data.CarRepository;
import pt.ua.deti.tqs.cars_service.services.CarManagerService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarManagerServiceTest {

    @Mock(lenient = true)
    private CarRepository repository;

    @InjectMocks
    private CarManagerService service;

    @BeforeEach
    void setUp() {

        Car car1 = new Car("Nissan", "Micra");
        car1.setCarId(20L);

        Car car2 = new Car("Ferrari", "Roma");
        car2.setCarId(42L);

        Car car3 = new Car("Opel", "Manta 400");
        car3.setCarId(5L);

        List<Car> allCars = Arrays.asList(car1, car2, car3);

        when(repository.save(car1)).thenReturn(car1);
        when(repository.save(car2)).thenReturn(car2);
        when(repository.save(car3)).thenReturn(car3);

        when(repository.findByCarId(car1.getCarId())).thenReturn(car1);
        when(repository.findByCarId(car2.getCarId())).thenReturn(car2);
        when(repository.findByCarId(car3.getCarId())).thenReturn(car3);
        when(repository.findAll()).thenReturn(allCars);

        // when(repository.findByCarId(-99L)).thenReturn(null);
    }

    @Test
    void save() {

        Car car1 = new Car("Nissan", "Micra");
        car1.setCarId(20L);

        Car car2 = new Car("Ferrari", "Roma");
        car2.setCarId(42L);

        Car car3 = new Car("Opel", "Manta 400");
        car3.setCarId(5L);

        List<Car> allCars = Arrays.asList(car1, car2, car3);

        for (Car car: allCars) {
            Car result = service.save(car);
            assertThat(result).isNotNull().isEqualTo(car);
        }

        verify(repository, times(3)).save(Mockito.any());

    }

    @Test
    void getAllCars() {

        Car car1 = new Car("Nissan", "Micra");
        car1.setCarId(20L);

        Car car2 = new Car("Ferrari", "Roma");
        car2.setCarId(42L);

        Car car3 = new Car("Opel", "Manta 400");
        car3.setCarId(5L);

        List<Car> allCars = service.getAllCars();

        // CarId
        assertThat(allCars).hasSize(3).extracting(Car::getCarId).contains(car1.getCarId(), car2.getCarId(), car3.getCarId());

        // Maker
        assertThat(allCars).hasSize(3).extracting(Car::getMaker).contains(car1.getMaker(), car2.getMaker(), car3.getMaker());

        // Model
        assertThat(allCars).hasSize(3).extracting(Car::getModel).contains(car1.getModel(), car2.getModel(), car3.getModel());

        verify(repository, times(1)).findAll();
    }

    @Test
    void getCarDetails() {

        Car car1 = new Car("Nissan", "Micra");
        car1.setCarId(20L);

        Car car2 = new Car("Ferrari", "Roma");
        car2.setCarId(42L);

        Car car3 = new Car("Opel", "Manta 400");
        car3.setCarId(5L);

        List<Car> allCars = Arrays.asList(car1, car2, car3);

        for (Car car: allCars) {
            Car result = service.getCarDetails(car.getCarId());
            assertThat(result).isNotNull().isEqualTo(car);
        }

        verify(repository, times(3)).findByCarId(Mockito.any());
    }

    @Test
    void getCarDetailsNonExistent() {
        Car result = service.getCarDetails(-99L);
        assertThat(result).isNull();

        verify(repository, times(1)).findByCarId(-99L);
    }
}