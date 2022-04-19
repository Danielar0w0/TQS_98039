package pt.ua.deti.tqs.hw1.project_api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pt.ua.deti.tqs.hw1.project_api.models.*;

import java.util.List;

@Service
public class RemoteAPIService {

    private RestTemplate restTemplate;
    private ObjectMapper objectMap;

    @Autowired
    public RemoteAPIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMap = new ObjectMapper();
    }

    public String obtainIso(String country) {

        ResponseEntity<Country[]> responseCountries = restTemplate.getForEntity("/npm-covid-data/countries-name-ordered", Country[].class);
        Country[] countries = responseCountries.getBody();

        for (Country c: countries) {
            if (c.getName().equalsIgnoreCase(country)) {
                return c.getIso();
            }
        }

        return null;
    }

    public CountryData[] getWorldData() {
        ResponseEntity<CountryData[]> response = restTemplate.getForEntity("/npm-covid-data/world", CountryData[].class);
        CountryData[] covidData = response.getBody();
        return covidData;
    }

    public CountryData[] getAllCountriesData() {
        ResponseEntity<CountryData[]> response = restTemplate.getForEntity("/npm-covid-data/", CountryData[].class);
        CountryData[] covidData = response.getBody();
        return covidData;
    }

    public CountryData[] getCountryData(String country) {

        // Obtain iso for given country, if possible
        String iso = obtainIso(country);

        if (!iso.isEmpty() && !iso.isBlank()) {

            // Convert country to acceptable format
            country = country.substring(0, 1).toUpperCase() + country.substring(1).toLowerCase();

            ResponseEntity<CountryData[]> response = restTemplate.getForEntity("/npm-covid-data/country-report-iso-based/" + country + "/" + iso, CountryData[].class);
            CountryData[] covidData = response.getBody();
            return covidData;
        }

        return null;
    }

    public StateData[] getCountryStatesData(String country) {

        // Obtain iso for given country, if possible
        String iso = obtainIso(country);

        if (!iso.isEmpty() && !iso.isBlank()) {
            ResponseEntity<StateData[]> response = restTemplate.getForEntity("/api-covid-data/reports/" + iso.toUpperCase(), StateData[].class);
            StateData[] covidData = response.getBody();
            return covidData;
        }

        return null;
    }

    public RecentData[] getRecentCountryData(String country) {

        // Obtain iso for given country, if possible
        String iso = obtainIso(country);

        if (!iso.isEmpty() && !iso.isBlank()) {
            ResponseEntity<RecentData[]> response = restTemplate.getForEntity("/covid-ovid-data/sixmonth/" + iso.toUpperCase(), RecentData[].class);
            RecentData[] covidData = response.getBody();
            return covidData;
        }

        return null;
    }

    public List<News> getAllCovidNews() throws JsonProcessingException {
        JSONObject jsonObject = restTemplate.getForObject("/news/get-coronavirus-news/0", JSONObject.class);
        String jsonNews = objectMap.writeValueAsString(jsonObject.get("news"));

        return objectMap.readValue(jsonNews, List.class);
    }

    public List<News> getAllHealthNews() throws JsonProcessingException {
        JSONObject jsonObject = restTemplate.getForObject("/news/get-health-news/1", JSONObject.class);
        String jsonNews = objectMap.writeValueAsString(jsonObject.get("news"));

        return objectMap.readValue(jsonNews, List.class);
    }

    public List<News> getAllVaccineNews() throws JsonProcessingException {
        JSONObject jsonObject = restTemplate.getForObject("/news/get-vaccine-news/0", JSONObject.class);
        String jsonNews = objectMap.writeValueAsString(jsonObject.get("news"));

        return objectMap.readValue(jsonNews, List.class);
    }





}
