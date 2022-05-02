package pt.ua.deti.tqs.hw1.project_api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pt.ua.deti.tqs.hw1.project_api.models.*;
import pt.ua.deti.tqs.hw1.project_api.services.RemoteAPIService;

import java.util.List;

@PropertySource("classpath:application.properties")
@RestController
@RequestMapping("/api")
public class APIController {

    private static Logger logger = LoggerFactory.getLogger(APIController.class);
    private RemoteAPIService service;

    @Autowired
    public APIController(RemoteAPIService service) {
        this.service = service;
    }

    @GetMapping("/world")
    public ResponseEntity<CountryData[]> getWorldData() {
        logger.info("Called getWorldData()");
        CountryData[] covidData = service.getWorldData();
        return ResponseEntity.ok().body(covidData);
    }

    @GetMapping("/countries/all")
    public ResponseEntity<CountryData[]> getAllCountriesData() {
        logger.info("Called getAllCountriesData()");
        CountryData[] covidData = service.getAllCountriesData();
        return ResponseEntity.ok().body(covidData);
    }

    @GetMapping("/countries/{country}")
    public ResponseEntity<CountryData[]> getCountryData(@PathVariable String country) {
        logger.info("Called getCountryData()");
        try {
            CountryData[] covidData = service.getCountryData(country);
            return ResponseEntity.ok().body(covidData);
        } catch (NullPointerException ex) {
            return ResponseEntity.ok().body(null);
        }
    }

    @GetMapping("/recent/{country}")
    public ResponseEntity<RecentData[]> getRecentCountryData(@PathVariable String country) {
        logger.info("Called getRecentCountryData()");
        try {
            RecentData[] covidData = service.getRecentCountryData(country);
            return ResponseEntity.ok().body(covidData);
        } catch (NullPointerException ex) {
            return ResponseEntity.ok().body(null);
        }
    }

    @GetMapping("/news/covid")
    public ResponseEntity<List<News>> getAllCovidNews() throws JsonProcessingException {
        logger.info("Called getAllCovidNews()");
        List<News> news = service.getAllCovidNews();
        return ResponseEntity.ok().body(news);
    }

    @GetMapping("/news/health")
    public ResponseEntity<List<News>> getAllHealthNews() throws JsonProcessingException {
        logger.info("Called getAllHealthNews()");
        List<News> news = service.getAllHealthNews();
        return ResponseEntity.ok().body(news);
    }

    @GetMapping("/news/vaccine")
    public ResponseEntity<List<News>> getAllVaccineNews() throws JsonProcessingException {
        logger.info("Called getAllVaccineNews()");
        List<News> news = service.getAllVaccineNews();
        return ResponseEntity.ok().body(news);
    }

    @GetMapping("/list")
    public ResponseEntity<Country[]> getCountriesList() {
        logger.info("Called getCountriesList()");
        Country[] countries = service.getCountriesList();
        return ResponseEntity.ok().body(countries);
    }
}
