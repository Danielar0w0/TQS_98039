package pt.ua.deti.tqs.hw1.project_api.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pt.ua.deti.tqs.hw1.project_api.cache.CacheManager;
import pt.ua.deti.tqs.hw1.project_api.cache.CacheObject;
import pt.ua.deti.tqs.hw1.project_api.models.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class RemoteAPIService {

    private static Logger logger = LoggerFactory.getLogger(RemoteAPIService.class);

    private RestTemplate restTemplate;
    private ObjectMapper objectMap;
    private CacheManager cacheManager;

    @Autowired
    public RemoteAPIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMap = new ObjectMapper();
        this.cacheManager = CacheManager.getInstance();
    }

    public String obtainIso(String country) {

        logger.info("Obtaining Iso's...");
        Country[] countries;
        CacheObject cache = cacheManager.get("obtainIso");

        if (cache != null) {
            countries = (Country[]) cache.getContent();
            logger.info("Used Cache");
        } else {
            ResponseEntity<Country[]> responseCountries = restTemplate.getForEntity("/npm-covid-data/countries-name-ordered", Country[].class);
            countries = responseCountries.getBody();
            cacheManager.put("obtainIso", countries);
            logger.info("Saved Iso's to Cache");
        }

        for (Country c: countries) {
            if (c.getName().equalsIgnoreCase(country)) {
                logger.info("ISO: " + c.getIso());
                return c.getIso();
            }
        }

        logger.warn("Unable to find iso for country: " + country);
        return null;
    }

    public CountryData[] getWorldData() {

        logger.info("Obtaining world data...");
        CountryData[] covidData;
        CacheObject cache = cacheManager.get("getWorldData");

        if (cache != null) {
            covidData = (CountryData[]) cache.getContent();
            logger.info("Used Cache");
        } else {
            ResponseEntity<CountryData[]> response = restTemplate.getForEntity("/npm-covid-data/world", CountryData[].class);
            covidData = response.getBody();
            cacheManager.put("getWorldData", covidData);
            logger.info("Saved world data to Cache");
        }
        return covidData;
    }

    public CountryData[] getAllCountriesData() {

        logger.info("Obtaining all countries' data...");
        CountryData[] covidData;
        CacheObject cache = cacheManager.get("getAllCountriesData");

        if (cache != null) {
            covidData = (CountryData[]) cache.getContent();
            logger.info("Used Cache");
        } else {
            ResponseEntity<CountryData[]> response = restTemplate.getForEntity("/npm-covid-data/", CountryData[].class);
            covidData = response.getBody();
            cacheManager.put("getAllCountriesData", covidData);
            logger.info("Saved countries data to Cache");
        }
        return covidData;
    }

    public CountryData[] getCountryData(String country) {

        logger.info("Obtaining country data (" + country + ")...");

        CountryData[] covidData;
        CacheObject cache = cacheManager.get("getCountryData-" + country);

        if (cache != null) {
            covidData = (CountryData[]) cache.getContent();
            logger.info("Used Cache");

            return covidData;
        }

        // Obtain iso for given country, if possible
        String iso = obtainIso(country);

        if (iso != null && !iso.isEmpty() && !iso.isBlank()) {

            // Convert country to acceptable format
            country = country.substring(0, 1).toUpperCase() + country.substring(1).toLowerCase();
            ResponseEntity<CountryData[]> response = restTemplate.getForEntity("/npm-covid-data/country-report-iso-based/" + country + "/" + iso, CountryData[].class);
            covidData = response.getBody();

            cacheManager.put("getCountryData-" + country.toLowerCase(), covidData);
            logger.info("Saved country data to Cache");

            return covidData;
        }
        return null;
    }

    public RecentData[] getRecentCountryData(String country) {

        logger.info("Obtaining recent country data (" + country + ")...");

        RecentData[] covidData;
        CacheObject cache = cacheManager.get("getRecentCountryData-" + country);

        if (cache != null) {
            covidData = (RecentData[]) cache.getContent();
            logger.info("Used Cache");
            return covidData;
        }

        // Obtain iso for given country, if possible
        String iso = obtainIso(country);

        if (iso != null && !iso.isEmpty() && !iso.isBlank()) {

            ResponseEntity<RecentData[]> response = restTemplate.getForEntity("/covid-ovid-data/sixmonth/" + iso.toUpperCase(), RecentData[].class);
            covidData = response.getBody();

            cacheManager.put("getRecentCountryData-" + country, covidData);
            logger.info("Saved recent country data to Cache");

            return covidData;
        }
        return null;
    }

    public List<News> getAllCovidNews() throws JsonProcessingException {

        logger.info("Obtaining all covid news...");
        List<News> news = new ArrayList<>();
        CacheObject cache = cacheManager.get("getAllCovidNews");

        if (cache != null) {
            news = (List<News>) cache.getContent();
            logger.info("Used Cache");
        } else {
            JSONObject jsonObject = restTemplate.getForObject("/news/get-coronavirus-news/0", JSONObject.class);
            if (jsonObject != null) {
                String jsonNews = objectMap.writeValueAsString(jsonObject.get("news"));
                news = objectMap.readValue(jsonNews, List.class);
                cacheManager.put("getAllCovidNews", news);
                logger.info("Saved covid news to Cache");
            }
        }
        return news;
    }

    public List<News> getAllHealthNews() throws JsonProcessingException {

        logger.info("Obtaining all health news...");
        List<News> news = new ArrayList<>();
        CacheObject cache = cacheManager.get("getAllHealthNews");

        if (cache != null) {
            news = (List<News>) cache.getContent();
            logger.info("Used Cache");
        } else {
            JSONObject jsonObject = restTemplate.getForObject("/news/get-health-news/1", JSONObject.class);
            if (jsonObject != null) {
                String jsonNews = objectMap.writeValueAsString(jsonObject.get("news"));
                news = objectMap.readValue(jsonNews, List.class);
                cacheManager.put("getAllHealthNews", news);
                logger.info("Saved health news to Cache");
            }
        }
        return news;
    }

    public List<News> getAllVaccineNews() throws JsonProcessingException {

        logger.info("Obtaining all vaccine news...");
        List<News> news = new ArrayList<>();
        CacheObject cache = cacheManager.get("getAllVaccineNews");

        if (cache != null) {
            news = (List<News>) cache.getContent();
            logger.info("Used Cache");
        } else {
            JSONObject jsonObject = restTemplate.getForObject("/news/get-vaccine-news/0", JSONObject.class);
            if (jsonObject != null) {
                String jsonNews = objectMap.writeValueAsString(jsonObject.get("news"));
                news = objectMap.readValue(jsonNews, List.class);
                cacheManager.put("getAllVaccineNews", news);
                logger.info("Saved vaccines news to Cache");
            }
        }
        return news;
    }

    public Country[] getCountriesList() {

        logger.info("Obtaining all list of countries...");
        Country[] countries;
        CacheObject cache = cacheManager.get("getAllCountries");

        if (cache != null) {
            countries = (Country[]) cache.getContent();
            logger.info("Used Cache");
        } else {
            ResponseEntity<Country[]> response = restTemplate.getForEntity("/npm-covid-data/countries-name-ordered", Country[].class);
            countries = response.getBody();
            cacheManager.put("getAllCountries", countries);
            logger.info("Saved list of countries to Cache");
        }
        return countries;
    }

    public void clearCache() {
        this.cacheManager.clear();
    }
}
