package pt.ua.deti.tqs.hw1.project_api.integration_tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.hamcrest.core.IsNull;
import org.hamcrest.text.IsEmptyString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pt.ua.deti.tqs.hw1.project_api.controllers.APIController;
import pt.ua.deti.tqs.hw1.project_api.models.Country;
import pt.ua.deti.tqs.hw1.project_api.models.CountryData;
import pt.ua.deti.tqs.hw1.project_api.models.News;
import pt.ua.deti.tqs.hw1.project_api.models.RecentData;
import pt.ua.deti.tqs.hw1.project_api.services.RemoteAPIService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(APIController.class)
class APIControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RemoteAPIService service;

    private ObjectMapper objectMap;

    @BeforeEach
    void setUp() {
        this.objectMap = new ObjectMapper();
    }

    @Test
    void getWorldData() throws Exception {

        CountryData[] covidData = objectMap.readValue("[{\"id\": \"40f44943-8413-4a31-a4df-deee8111f85f\", \"rank\": 0, \"ThreeLetterSymbol\": null, \"Country\": \"World\", \"Continent\": \"All\"," +
                "\"Infection_Risk\": 0.0, \"Case_Fatality_Rate\": 1.22, \"Recovery_Proporation\": 91.04, \"TotalCases\": 513397013, \"NewCases\": 162614," +
                "\"TotalDeaths\": 6261081, \"TotalRecovered\": 467413524, \"NewRecovered\": 255078, \"ActiveCases\": 39722408, \"TotalTests\": 0," +
                "\"Population\": 0,\"Tests_1M_Pop\": 0, \"TotCases_1M_Pop\": 65864}]", CountryData[].class);

        when(service.getWorldData()).thenReturn(covidData);
        mvc.perform(get("/api/world").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].Country", is(covidData[0].getCountry())));

        verify(service, Mockito.times(1)).getWorldData();
    }

    @Test
    void getAllCountriesData() throws Exception {

        CountryData country1 = objectMap.readValue("{\"id\": \"40f44943-8413-4a31-a4df-deee8111f85f\", \"rank\": 0, \"ThreeLetterSymbol\": null, \"Country\": \"World\", \"Continent\": \"All\"," +
                "\"Infection_Risk\": 0.0, \"Case_Fatality_Rate\": 1.22, \"Recovery_Proporation\": 91.04, \"TotalCases\": 513397013, \"NewCases\": 162614," +
                "\"TotalDeaths\": 6261081, \"TotalRecovered\": 467413524, \"NewRecovered\": 255078, \"ActiveCases\": 39722408, \"TotalTests\": 0," +
                "\"Population\": 0,\"Tests_1M_Pop\": 0, \"TotCases_1M_Pop\": 65864}", CountryData.class);

        CountryData country2 = objectMap.readValue("{\"id\": \"40fdsdg43-8413-4a31-a4df-dedf79khf2\", \"rank\": 1, \"ThreeLetterSymbol\": \"usa\", \"Country\": \"USA\", \"Continent\": \"All\"," +
                "\"Infection_Risk\": 0.0, \"Case_Fatality_Rate\": 1.22, \"Recovery_Proporation\": 91.04, \"TotalCases\": 513397013, \"NewCases\": 162614," +
                "\"TotalDeaths\": 6261081, \"TotalRecovered\": 467413524, \"NewRecovered\": 255078, \"ActiveCases\": 39722408, \"TotalTests\": 0," +
                "\"Population\": 0,\"Tests_1M_Pop\": 0, \"TotCases_1M_Pop\": 65864}", CountryData.class);

        CountryData[] countries = {country1, country2};

        when(service.getAllCountriesData()).thenReturn(countries);
        mvc.perform(get("/api/countries/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].Country", is(country1.getCountry())))
                .andExpect(jsonPath("$[1].Country", is(country2.getCountry())));

        verify(service, Mockito.times(1)).getAllCountriesData();
    }

    @Test
    void getCountryData() throws Exception {

        String name = "USA"; String iso = "usa";
        CountryData country = objectMap.readValue("{\"id\": \"40fdsdg43-8413-4a31-a4df-dedf79khf2\", \"rank\": 1, \"ThreeLetterSymbol\": \"" + iso + "\", \"Country\": \"" + name + "\", \"Continent\": \"All\"," +
                "\"Infection_Risk\": 0.0, \"Case_Fatality_Rate\": 1.22, \"Recovery_Proporation\": 91.04, \"TotalCases\": 513397013, \"NewCases\": 162614," +
                "\"TotalDeaths\": 6261081, \"TotalRecovered\": 467413524, \"NewRecovered\": 255078, \"ActiveCases\": 39722408, \"TotalTests\": 0," +
                "\"Population\": 0,\"Tests_1M_Pop\": 0, \"TotCases_1M_Pop\": 65864}", CountryData.class);

        when(service.getCountryData(name)).thenReturn(new CountryData[] {country});
        mvc.perform(get("/api/countries/" + name).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].Country", is(country.getCountry())))
                .andExpect(jsonPath("$[0].ThreeLetterSymbol", is(country.getIso())));

        verify(service, Mockito.times(1)).getCountryData(name);
    }

    @Test
    void getInvalidCountryData() throws Exception {

        String name = "Random";
        when(service.getCountryData(name)).thenReturn(null);
        mvc.perform(get("/api/countries/" + name).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((content().string(IsEmptyString.isEmptyOrNullString())));

        verify(service, Mockito.times(1)).getCountryData(name);
    }

    @Test
    void getRecentCountryData() throws Exception {

        String name = "USA";
        RecentData country = objectMap.readValue("{" +
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
                "}", RecentData.class);

        when(service.getRecentCountryData(name)).thenReturn(new RecentData[] {country});
        mvc.perform(get("/api/recent/" + name).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].Country", is(country.getCountry())))
                .andExpect(jsonPath("$[0].symbol", is(country.getIso())));

        verify(service, Mockito.times(1)).getRecentCountryData(name);
    }

    @Test
    void getInvalidRecentCountryData() throws Exception {

        String name = "Random";
        when(service.getRecentCountryData(name)).thenReturn(null);
        mvc.perform(get("/api/recent/" + name).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((content().string(IsEmptyString.isEmptyOrNullString())));

        verify(service, Mockito.times(1)).getRecentCountryData(name);
    }

    @Test
    void getAllCovidNews() throws Exception {

        String title = "Passengers at Vancouver airport advised to arrive hours before flight, as security screening creates delays | CBC News";
        String json = "{\"news\": [{\"news_id\": \"06f0b9ef-40d9-4370-9e0e-6ab6d079d957\", \"title\": \"" + title + "\"," +
                "\"urlToImage\": \"https://i.cbc.ca/1.5526284.1586363874!/cumulusImage/httpImage/image.jpg_gen/derivatives/16x9_620/covid-19-travel-vancouver-airport.jpg\"," +
                "\"imageInLocalStorage\": \"/logo-pic-resized.jpg\", \"imageFileName\": \"Sun May 01 2022 06:37:23-cbc-news.jpg\", \"pubDate\": \"2022-05-01T06:37:23.724Z\"," +
                "\"content\": \"As COVID-19 travel restrictions continue to ease, passengers with flights departing from Vancouver International Airport are being asked to arrive hours ahead of their scheduled flights.\"," +
                "\"reference\": \"cbc-news\"}]}";

        JSONObject jsonObject = objectMap.readValue(json, JSONObject.class);

        String jsonNews = objectMap.writeValueAsString(jsonObject.get("news"));
        List<News> news = objectMap.readValue(jsonNews, List.class);

        when(service.getAllCovidNews()).thenReturn(news);
        mvc.perform(get("/api/news/covid").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(title)));

        verify(service, Mockito.times(1)).getAllCovidNews();
    }

    @Test
    void getAllHealthNews() throws Exception {

        String title = "Passengers at Vancouver airport advised to arrive hours before flight, as security screening creates delays | CBC News";
        String json = "{\"news\": [{\"news_id\": \"06f0b9ef-40d9-4370-9e0e-6ab6d079d957\", \"title\": \"" + title + "\"," +
                "\"urlToImage\": \"https://i.cbc.ca/1.5526284.1586363874!/cumulusImage/httpImage/image.jpg_gen/derivatives/16x9_620/covid-19-travel-vancouver-airport.jpg\"," +
                "\"imageInLocalStorage\": \"/logo-pic-resized.jpg\", \"imageFileName\": \"Sun May 01 2022 06:37:23-cbc-news.jpg\", \"pubDate\": \"2022-05-01T06:37:23.724Z\"," +
                "\"content\": \"As COVID-19 travel restrictions continue to ease, passengers with flights departing from Vancouver International Airport are being asked to arrive hours ahead of their scheduled flights.\"," +
                "\"reference\": \"cbc-news\"}]}";

        JSONObject jsonObject = objectMap.readValue(json, JSONObject.class);

        String jsonNews = objectMap.writeValueAsString(jsonObject.get("news"));
        List<News> news = objectMap.readValue(jsonNews, List.class);

        when(service.getAllHealthNews()).thenReturn(news);
        mvc.perform(get("/api/news/health").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(title)));

        verify(service, Mockito.times(1)).getAllHealthNews();

    }

    @Test
    void getAllVaccineNews() throws Exception {

        String title = "Passengers at Vancouver airport advised to arrive hours before flight, as security screening creates delays | CBC News";
        String json = "{\"news\": [{\"news_id\": \"06f0b9ef-40d9-4370-9e0e-6ab6d079d957\", \"title\": \"" + title + "\"," +
                "\"urlToImage\": \"https://i.cbc.ca/1.5526284.1586363874!/cumulusImage/httpImage/image.jpg_gen/derivatives/16x9_620/covid-19-travel-vancouver-airport.jpg\"," +
                "\"imageInLocalStorage\": \"/logo-pic-resized.jpg\", \"imageFileName\": \"Sun May 01 2022 06:37:23-cbc-news.jpg\", \"pubDate\": \"2022-05-01T06:37:23.724Z\"," +
                "\"content\": \"As COVID-19 travel restrictions continue to ease, passengers with flights departing from Vancouver International Airport are being asked to arrive hours ahead of their scheduled flights.\"," +
                "\"reference\": \"cbc-news\"}]}";

        JSONObject jsonObject = objectMap.readValue(json, JSONObject.class);

        String jsonNews = objectMap.writeValueAsString(jsonObject.get("news"));
        List<News> news = objectMap.readValue(jsonNews, List.class);

        when(service.getAllVaccineNews()).thenReturn(news);
        mvc.perform(get("/api/news/vaccine").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(title)));

        verify(service, Mockito.times(1)).getAllVaccineNews();
    }

    @Test
    void getCountriesList() throws Exception {

        Country country1 = objectMap.readValue("{\"Country\": \"Kenya\", \"iso\": \"ken\"}", Country.class);
        Country country2 = objectMap.readValue("{\"Country\": \"USA\", \"iso\": \"usa\"}", Country.class);

        Country[] countries = {country1, country2};
        when(service.getCountriesList()).thenReturn(countries);
        mvc.perform(get("/api/list").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].Country", is(country1.getName())))
                .andExpect(jsonPath("$[1].Country", is(country2.getName())));

        verify(service, Mockito.times(1)).getCountriesList();
    }
}