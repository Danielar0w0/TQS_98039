package pt.ua.deti.tqs.hw1.project_api.service_tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pt.ua.deti.tqs.hw1.project_api.models.Country;
import pt.ua.deti.tqs.hw1.project_api.models.CountryData;
import pt.ua.deti.tqs.hw1.project_api.models.News;
import pt.ua.deti.tqs.hw1.project_api.services.RemoteAPIService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RemoteAPIServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private RemoteAPIService service;
    private ObjectMapper objectMap;

    @BeforeEach
    void setUp() {
        service = new RemoteAPIService(restTemplate);
        this.objectMap = new ObjectMapper();
    }

    @AfterEach
    void clearCache() {
        service.clearCache();
    }

    @Test
    void getWorldData() throws JsonProcessingException {

        CountryData[] covidData = objectMap.readValue("[{\"id\": \"40f44943-8413-4a31-a4df-deee8111f85f\", \"rank\": 0, \"ThreeLetterSymbol\": null, \"Country\": \"World\", \"Continent\": \"All\"," +
                "\"Infection_Risk\": 0.0, \"Case_Fatality_Rate\": 1.22, \"Recovery_Proporation\": 91.04, \"TotalCases\": 513397013, \"NewCases\": 162614," +
                "\"TotalDeaths\": 6261081, \"TotalRecovered\": 467413524, \"NewRecovered\": 255078, \"ActiveCases\": 39722408, \"TotalTests\": 0," +
                "\"Population\": 0,\"Tests_1M_Pop\": 0, \"TotCases_1M_Pop\": 65864}]", CountryData[].class);

        ResponseEntity<CountryData[]> response = ResponseEntity.ok().body(covidData);
        Mockito.when(restTemplate.getForEntity("/npm-covid-data/world", CountryData[].class)).thenReturn(response);

        CountryData[] result = service.getWorldData();

        assertEquals(result, covidData);
        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity("/npm-covid-data/world", CountryData[].class);

        service.getWorldData();
        // Cache enabled!
        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity("/npm-covid-data/world", CountryData[].class);
    }

    @Test
    void getAllCountriesData() throws JsonProcessingException {

        CountryData country1 = objectMap.readValue("{\"id\": \"40f44943-8413-4a31-a4df-deee8111f85f\", \"rank\": 0, \"ThreeLetterSymbol\": null, \"Country\": \"World\", \"Continent\": \"All\"," +
                "\"Infection_Risk\": 0.0, \"Case_Fatality_Rate\": 1.22, \"Recovery_Proporation\": 91.04, \"TotalCases\": 513397013, \"NewCases\": 162614," +
                "\"TotalDeaths\": 6261081, \"TotalRecovered\": 467413524, \"NewRecovered\": 255078, \"ActiveCases\": 39722408, \"TotalTests\": 0," +
                "\"Population\": 0,\"Tests_1M_Pop\": 0, \"TotCases_1M_Pop\": 65864}", CountryData.class);

        CountryData country2 = objectMap.readValue("{\"id\": \"40fdsdg43-8413-4a31-a4df-dedf79khf2\", \"rank\": 1, \"ThreeLetterSymbol\": \"usa\", \"Country\": \"USA\", \"Continent\": \"All\"," +
                "\"Infection_Risk\": 0.0, \"Case_Fatality_Rate\": 1.22, \"Recovery_Proporation\": 91.04, \"TotalCases\": 513397013, \"NewCases\": 162614," +
                "\"TotalDeaths\": 6261081, \"TotalRecovered\": 467413524, \"NewRecovered\": 255078, \"ActiveCases\": 39722408, \"TotalTests\": 0," +
                "\"Population\": 0,\"Tests_1M_Pop\": 0, \"TotCases_1M_Pop\": 65864}", CountryData.class);

        CountryData[] countries = {country1, country2};
        ResponseEntity<CountryData[]> response = ResponseEntity.ok().body(countries);

        Mockito.when(restTemplate.getForEntity("/npm-covid-data/", CountryData[].class)).thenReturn(response);

        CountryData[] result = service.getAllCountriesData();

        assertEquals(result, countries);
        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity("/npm-covid-data/", CountryData[].class);

        service.getAllCountriesData();
        // Cache enabled!
        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity("/npm-covid-data/", CountryData[].class);
    }

    @Test
    void getAllCovidNews() throws JsonProcessingException {

        String json = "{\"news\": [{\"news_id\": \"06f0b9ef-40d9-4370-9e0e-6ab6d079d957\", \"title\": \"Passengers at Vancouver airport advised to arrive hours before flight, as security screening creates delays | CBC News\"," +
                "\"urlToImage\": \"https://i.cbc.ca/1.5526284.1586363874!/cumulusImage/httpImage/image.jpg_gen/derivatives/16x9_620/covid-19-travel-vancouver-airport.jpg\"," +
                "\"imageInLocalStorage\": \"/logo-pic-resized.jpg\", \"imageFileName\": \"Sun May 01 2022 06:37:23-cbc-news.jpg\", \"pubDate\": \"2022-05-01T06:37:23.724Z\"," +
                "\"content\": \"As COVID-19 travel restrictions continue to ease, passengers with flights departing from Vancouver International Airport are being asked to arrive hours ahead of their scheduled flights.\"," +
                "\"reference\": \"cbc-news\"}]}";

        JSONObject jsonObject = objectMap.readValue(json, JSONObject.class);

        String jsonNews = objectMap.writeValueAsString(jsonObject.get("news"));
        List<News> news = objectMap.readValue(jsonNews, List.class);

        Mockito.when(restTemplate.getForObject("/news/get-coronavirus-news/0", JSONObject.class)).thenReturn(jsonObject);

        List<News> result = service.getAllCovidNews();

        assertEquals(result, news);
        Mockito.verify(restTemplate, Mockito.times(1)).getForObject("/news/get-coronavirus-news/0", JSONObject.class);

        service.getAllCovidNews();
        // Cache enabled!
        Mockito.verify(restTemplate, Mockito.times(1)).getForObject("/news/get-coronavirus-news/0", JSONObject.class);
    }

    @Test
    void getAllHealthNews() throws JsonProcessingException {

        String json = "{\"news\": [{\"news_id\": \"06f0b9ef-40d9-4370-9e0e-6ab6d079d957\", \"title\": \"Passengers at Vancouver airport advised to arrive hours before flight, as security screening creates delays | CBC News\"," +
                "\"urlToImage\": \"https://i.cbc.ca/1.5526284.1586363874!/cumulusImage/httpImage/image.jpg_gen/derivatives/16x9_620/covid-19-travel-vancouver-airport.jpg\"," +
                "\"imageInLocalStorage\": \"/logo-pic-resized.jpg\", \"imageFileName\": \"Sun May 01 2022 06:37:23-cbc-news.jpg\", \"pubDate\": \"2022-05-01T06:37:23.724Z\"," +
                "\"content\": \"As COVID-19 travel restrictions continue to ease, passengers with flights departing from Vancouver International Airport are being asked to arrive hours ahead of their scheduled flights.\"," +
                "\"reference\": \"cbc-news\"}]}";

        JSONObject jsonObject = objectMap.readValue(json, JSONObject.class);

        String jsonNews = objectMap.writeValueAsString(jsonObject.get("news"));
        List<News> news = objectMap.readValue(jsonNews, List.class);

        Mockito.when(restTemplate.getForObject("/news/get-health-news/1", JSONObject.class)).thenReturn(jsonObject);

        List<News> result = service.getAllHealthNews();

        assertEquals(result, news);
        Mockito.verify(restTemplate, Mockito.times(1)).getForObject("/news/get-health-news/1", JSONObject.class);

        service.getAllHealthNews();
        // Cache enabled!
        Mockito.verify(restTemplate, Mockito.times(1)).getForObject("/news/get-health-news/1", JSONObject.class);
    }

    @Test
    void getAllVaccineNews() throws JsonProcessingException {

        String json = "{\"news\": [{\"news_id\": \"06f0b9ef-40d9-4370-9e0e-6ab6d079d957\", \"title\": \"Passengers at Vancouver airport advised to arrive hours before flight, as security screening creates delays | CBC News\"," +
                "\"urlToImage\": \"https://i.cbc.ca/1.5526284.1586363874!/cumulusImage/httpImage/image.jpg_gen/derivatives/16x9_620/covid-19-travel-vancouver-airport.jpg\"," +
                "\"imageInLocalStorage\": \"/logo-pic-resized.jpg\", \"imageFileName\": \"Sun May 01 2022 06:37:23-cbc-news.jpg\", \"pubDate\": \"2022-05-01T06:37:23.724Z\"," +
                "\"content\": \"As COVID-19 travel restrictions continue to ease, passengers with flights departing from Vancouver International Airport are being asked to arrive hours ahead of their scheduled flights.\"," +
                "\"reference\": \"cbc-news\"}]}";

        JSONObject jsonObject = objectMap.readValue(json, JSONObject.class);

        String jsonNews = objectMap.writeValueAsString(jsonObject.get("news"));
        List<News> news = objectMap.readValue(jsonNews, List.class);

        Mockito.when(restTemplate.getForObject("/news/get-vaccine-news/0", JSONObject.class)).thenReturn(jsonObject);

        List<News> result = service.getAllVaccineNews();

        assertEquals(result, news);
        Mockito.verify(restTemplate, Mockito.times(1)).getForObject("/news/get-vaccine-news/0", JSONObject.class);

        service.getAllVaccineNews();
        // Cache enabled!
        Mockito.verify(restTemplate, Mockito.times(1)).getForObject("/news/get-vaccine-news/0", JSONObject.class);
    }

    @Test
    void getCountriesList() throws JsonProcessingException {

        Country country1 = objectMap.readValue("{\"Country\": \"Kenya\", \"iso\": \"ken\"}", Country.class);
        Country country2 = objectMap.readValue("{\"Country\": \"USA\", \"iso\": \"usa\"}", Country.class);

        Country[] responseCountries = {country1, country2};
        ResponseEntity<Country[]> response = ResponseEntity.ok().body(responseCountries);
        Mockito.when(restTemplate.getForEntity("/npm-covid-data/countries-name-ordered", Country[].class)).thenReturn(response);

        Country[] result = service.getCountriesList();

        assertEquals(result, responseCountries);
        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity("/npm-covid-data/countries-name-ordered", Country[].class);

        service.getCountriesList();
        // Cache enabled!
        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity("/npm-covid-data/countries-name-ordered", Country[].class);
    }

}