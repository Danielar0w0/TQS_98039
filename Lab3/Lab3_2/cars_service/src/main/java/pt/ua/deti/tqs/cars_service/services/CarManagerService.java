package pt.ua.deti.tqs.cars_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.cars_service.data.Car;
import pt.ua.deti.tqs.cars_service.data.CarRepository;

import java.util.List;

@Service
public class CarManagerService {

    private CarRepository carRepository;

    @Autowired
    public CarManagerService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Car getCarDetails(Long id) {
        return carRepository.findByCarId(id);
    }

}
