package pt.ua.deti.tqs.cars_service.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ua.deti.tqs.cars_service.data.Car;
import pt.ua.deti.tqs.cars_service.services.CarManagerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CarController {

    private CarManagerService carManagerService;

    public CarController(CarManagerService carManagerService) {
        this.carManagerService = carManagerService;
    }

    @PostMapping("/cars")
    ResponseEntity<Car> createCar(@RequestBody Car car) {
        Car saved = carManagerService.save(car);
        return ResponseEntity.accepted().body(saved);
    }

    @GetMapping("/cars")
    ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carManagerService.getAllCars();
        return ResponseEntity.ok().body(cars);
    }

    @GetMapping("/cars/{carId}")
    ResponseEntity<Car> getCarById(@PathVariable(value="carId") Long id) {
        Car car = carManagerService.getCarDetails(id);
        return ResponseEntity.ok().body(car);
    }
}
