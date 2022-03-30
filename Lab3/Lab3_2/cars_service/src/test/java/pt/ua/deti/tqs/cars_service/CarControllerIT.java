package pt.ua.deti.tqs.cars_service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import pt.ua.deti.tqs.cars_service.data.Car;
import pt.ua.deti.tqs.cars_service.data.CarRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

// @AutoConfigureTestDatabase
@TestPropertySource(locations = "/application-integrationtest.properties")
public class CarControllerIT {

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

    @Test
    void createCar() {

        Car car = new Car("Nissan", "Micra");

        ResponseEntity<Car> response = restTemplate.postForEntity("/api/cars", car, Car.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);

        Car result = response.getBody();
        assertThat(result.getMaker()).isEqualTo(car.getMaker());
        assertThat(result.getModel()).isEqualTo(car.getModel());

        result = repository.findByCarId(result.getCarId());
        assertThat(result.getMaker()).isEqualTo(car.getMaker());
        assertThat(result.getModel()).isEqualTo(car.getModel());
    }

    @Test
    void getAllCars() throws Exception {

        Car car1 = new Car("Nissan", "Micra");
        Car car2 = new Car("Ferrari", "Roma");

        repository.saveAndFlush(car1);
        repository.saveAndFlush(car2);

        ResponseEntity<List<Car>> response = restTemplate.exchange("/api/cars", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {
        });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getCarId).containsExactly(car1.getCarId(), car2.getCarId());
    }

    @Test
    void getCarById() throws Exception {

        Car car = new Car("Nissan", "Micra");
        repository.saveAndFlush(car);

        ResponseEntity<Car> response = restTemplate.exchange("/api/cars/" + car.getCarId(), HttpMethod.GET, null, new ParameterizedTypeReference<Car>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Car result = response.getBody();
        assertThat(result.getMaker()).isEqualTo(car.getMaker());
        assertThat(result.getModel()).isEqualTo(car.getModel());
    }


}
