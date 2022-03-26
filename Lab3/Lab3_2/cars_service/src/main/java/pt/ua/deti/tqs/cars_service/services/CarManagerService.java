package pt.ua.deti.tqs.cars_service.services;

import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.cars_service.data.Car;
import pt.ua.deti.tqs.cars_service.data.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarManagerService {

    private CarRepository carRepository;

    public Car save(Car car) {
        return null;
    }

    public List<Car> getAllCars() {
        return null;
    }

    public Optional<Car> getCarDetails(Long id) {
        return null;
    }

}
