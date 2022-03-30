package pt.ua.deti.tqs.cars_service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pt.ua.deti.tqs.cars_service.controllers.CarController;
import pt.ua.deti.tqs.cars_service.data.Car;
import pt.ua.deti.tqs.cars_service.services.CarManagerService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService carManagerService;

    @BeforeEach
    void setUp() throws Exception {
    }

    @Test
    void createCar() throws Exception {

        Car car = new Car("Nissan", "Micra");

        when(carManagerService.save(Mockito.any())).thenReturn(car);
        mvc.perform(post("/api/cars").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(car)))
            .andExpect(status().isAccepted())
            .andExpect(jsonPath("$.maker", is(car.getMaker())))
            .andExpect(jsonPath("$.model", is(car.getModel())));

        verify(carManagerService, times(1)).save(Mockito.any());
    }

    @Test
    void getAllCars() throws Exception {

        Car car1 = new Car("Nissan", "Micra");
        Car car2 = new Car("Ferrari", "Roma");

        List<Car> cars = Arrays.asList(car1, car2);
        when(carManagerService.getAllCars()).thenReturn(cars);

        mvc.perform(get("/api/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].maker", is(car1.getMaker())))
                .andExpect(jsonPath("$[0].model", is(car1.getModel())))
                .andExpect(jsonPath("$[1].maker", is(car2.getMaker())))
                .andExpect(jsonPath("$[1].model", is(car2.getModel())));

        verify(carManagerService, times(1)).getAllCars();
    }

    @Test
    void getCarById() throws Exception {

        Car car = new Car("Nissan", "Micra");

        when(carManagerService.getCarDetails(Mockito.any())).thenReturn(car);

        mvc.perform(get("/api/cars/0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maker", is(car.getMaker())))
                .andExpect(jsonPath("$.model", is(car.getModel())));

        verify(carManagerService, times(1)).getCarDetails(Mockito.any());
    }
}