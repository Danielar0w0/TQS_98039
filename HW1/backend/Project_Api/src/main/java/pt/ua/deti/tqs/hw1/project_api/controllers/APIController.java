package pt.ua.deti.tqs.hw1.project_api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
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

    private RemoteAPIService service;

    @Autowired
    public APIController(RemoteAPIService service) {
        this.service = service;
    }

    @GetMapping("/world")
    public CountryData[] getWorldData() {
        CountryData[] covidData = service.getWorldData();
        return covidData;
    }

    @GetMapping("/countries/all")
    public CountryData[] getAllCountriesData() {
        CountryData[] covidData = service.getAllCountriesData();
        return covidData;
    }

    @GetMapping("/countries/{country}")
    public CountryData[] getCountryData(@PathVariable String country) {
        CountryData[] covidData = service.getCountryData(country);
        return covidData;
    }

    @GetMapping("/states/{country}")
    public StateData[] getCountryStatesData(@PathVariable String country) {
        StateData[] covidData = service.getCountryStatesData(country);
        return covidData;
    }

    @GetMapping("/recent/{country}")
    public RecentData[] getRecentCountryData(@PathVariable String country) {
        RecentData[] covidData = service.getRecentCountryData(country);
        return covidData;
    }

    @GetMapping("/news/covid")
    public List<News> getAllCovidNews() throws JsonProcessingException {
        List<News> news = service.getAllCovidNews();
        return news;
    }

    @GetMapping("/news/health")
    public List<News> getAllHealthNews() throws JsonProcessingException {
        List<News> news = service.getAllHealthNews();
        return news;
    }

    @GetMapping("/news/vaccine")
    public List<News> getAllVaccineNews() throws JsonProcessingException {
        List<News> news = service.getAllVaccineNews();
        return news;
    }

    @GetMapping("/list")
    public Country[] getCountriesList() {
        Country[] countries = service.getCountriesList();
        return countries;
    }
}
