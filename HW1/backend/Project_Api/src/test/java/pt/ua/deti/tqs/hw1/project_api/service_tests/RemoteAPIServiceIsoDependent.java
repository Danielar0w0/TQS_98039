package pt.ua.deti.tqs.hw1.project_api.service_tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import pt.ua.deti.tqs.hw1.project_api.models.RecentData;
import pt.ua.deti.tqs.hw1.project_api.services.RemoteAPIService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RemoteAPIServiceIsoDependent {

    @Mock
    private RestTemplate restTemplate;

    private RemoteAPIService service;
    private ObjectMapper objectMap;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        service = new RemoteAPIService(restTemplate);
        this.objectMap = new ObjectMapper();

        Country country = objectMap.readValue("{\"Country\": \"USA\", \"ThreeLetterSymbol\": \"usa\"}", Country.class);

        Country[] responseCountries = {country};
        ResponseEntity<Country[]> response = ResponseEntity.ok().body(responseCountries);
        Mockito.when(restTemplate.getForEntity("/npm-covid-data/countries-name-ordered", Country[].class)).thenReturn(response);
    }

    @AfterEach
    void clearCache() {
        service.clearCache();
    }

    @Test
    void obtainIso() throws JsonProcessingException {

        Country country = objectMap.readValue("{\"Country\": \"USA\", \"ThreeLetterSymbol\": \"usa\"}", Country.class);

        String result = service.obtainIso(country.getName());
        assertEquals(result, country.getIso());

        result = service.obtainIso("Random");
        assertEquals(result, null);

        // Cache enabled!
        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity("/npm-covid-data/countries-name-ordered", Country[].class);
    }

    @Test
    void getCountryData() throws JsonProcessingException {

        String name = "USA"; String iso = "usa";
        CountryData country = objectMap.readValue("{\"id\": \"40fdsdg43-8413-4a31-a4df-dedf79khf2\", \"rank\": 1, \"ThreeLetterSymbol\": \"" + iso + "\", \"Country\": \"" + name + "\", \"Continent\": \"All\"," +
                "\"Infection_Risk\": 0.0, \"Case_Fatality_Rate\": 1.22, \"Recovery_Proporation\": 91.04, \"TotalCases\": 513397013, \"NewCases\": 162614," +
                "\"TotalDeaths\": 6261081, \"TotalRecovered\": 467413524, \"NewRecovered\": 255078, \"ActiveCases\": 39722408, \"TotalTests\": 0," +
                "\"Population\": 0,\"Tests_1M_Pop\": 0, \"TotCases_1M_Pop\": 65864}", CountryData.class);

        ResponseEntity<CountryData[]> response = ResponseEntity.ok().body(new CountryData[] {country});
        Mockito.when(restTemplate.getForEntity("/npm-covid-data/country-report-iso-based/Usa/usa", CountryData[].class)).thenReturn(response);

        CountryData result = service.getCountryData(name)[0];

        assertEquals(result, country);
        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity("/npm-covid-data/country-report-iso-based/Usa/usa", CountryData[].class);
        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity("/npm-covid-data/countries-name-ordered", Country[].class);
    }

    @Test
    void getRecentCountryData() throws JsonProcessingException {

        String name = "USA"; String iso = "usa";
        RecentData[] country = objectMap.readValue("[{" +
                "\"id\":\"c2eb998d-63e8-4819-9853-2381d92c86c5\"," +
                "\"symbol\":\"USA\"," +
                "\"Country\":\"United States\"," +
                "\"Continent\":\"North America\"," +
                "\"date\":\"2022-01-30\"," +
                "\"total_cases\":74333001," +
                "\"new_cases\":96887," +
                "\"total_deaths\":884260," +
                "\"new_deaths\":321," +
                "\"total_tests\":0," +
                "\"new_tests\":0" +
                "}]", RecentData[].class);

        ResponseEntity<RecentData[]> response = ResponseEntity.ok().body(country);

        Mockito.when(restTemplate.getForEntity("/covid-ovid-data/sixmonth/" + iso.toUpperCase(), RecentData[].class)).thenReturn(response);
        RecentData[] result = service.getRecentCountryData(name);

        assertEquals(result, country);
        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity("/covid-ovid-data/sixmonth/" + iso.toUpperCase(), RecentData[].class);
        Mockito.verify(restTemplate, Mockito.times(1)).getForEntity("/npm-covid-data/countries-name-ordered", Country[].class);
    }


}
