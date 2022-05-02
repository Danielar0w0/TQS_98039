package pt.ua.deti.tqs.hw1.project_api.unit_tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ua.deti.tqs.hw1.project_api.cache.CacheManager;
import pt.ua.deti.tqs.hw1.project_api.models.Country;

import static org.junit.jupiter.api.Assertions.*;

class CacheManagerTest {

    private CacheManager cacheManager;
    private ObjectMapper objectMap;

    @BeforeEach
    void setUp() {
        this.cacheManager = CacheManager.getInstance();
        this.objectMap = new ObjectMapper();
    }

    @Test
    void put() throws JsonProcessingException {

        Country country1 = objectMap.readValue("{\"Country\": \"Canada\", \"ThreeLetterSymbol\": \"CAN\"}", Country.class);
        Country country2 = objectMap.readValue("{\"Country\": \"Portugal\", \"ThreeLetterSymbol\": \"PT\"}", Country.class);

        cacheManager.put("country1", country1);
        cacheManager.put("country2", country2);

        assertEquals(2, cacheManager.size());

        // Overwrite behaviour
        cacheManager.put("country1", country2);
        assertEquals(2, cacheManager.size());

        cacheManager.put(null, country1);
        assertEquals(2, cacheManager.size());

        cacheManager.put("country1", null);
        assertEquals(1, cacheManager.size());
    }

    @Test
    void get() throws JsonProcessingException {

        Country country1 = objectMap.readValue("{\"Country\": \"Canada\", \"ThreeLetterSymbol\": \"CAN\"}", Country.class);
        Country country2 = objectMap.readValue("{\"Country\": \"Portugal\", \"ThreeLetterSymbol\": \"PT\"}", Country.class);

        cacheManager.put("country1", country1);
        cacheManager.put("country2", country2);

        Country result1 = (Country) cacheManager.get("country1").getContent();
        Country result2 = (Country) cacheManager.get("country2").getContent();

        assertEquals(country1, result1);
        assertEquals(country2, result2);
    }

    @Test
    void remove() throws JsonProcessingException {

        Country country1 = objectMap.readValue("{\"Country\": \"Canada\", \"ThreeLetterSymbol\": \"CAN\"}", Country.class);
        Country country2 = objectMap.readValue("{\"Country\": \"Portugal\", \"ThreeLetterSymbol\": \"PT\"}", Country.class);

        cacheManager.put("country1", country1);
        cacheManager.put("country2", country2);

        assertEquals(2, cacheManager.size());

        cacheManager.remove("country1");
        assertEquals(1, cacheManager.size());

        cacheManager.remove("random");
        assertEquals(1, cacheManager.size());
    }

    @Test
    void clear() throws JsonProcessingException {

        Country country1 = objectMap.readValue("{\"Country\": \"Canada\", \"ThreeLetterSymbol\": \"CAN\"}", Country.class);
        Country country2 = objectMap.readValue("{\"Country\": \"Portugal\", \"ThreeLetterSymbol\": \"PT\"}", Country.class);

        cacheManager.put("country1", country1);
        cacheManager.put("country2", country2);

        assertEquals(2, cacheManager.size());
        cacheManager.clear();

        assertEquals(0, cacheManager.size());
    }

    @Test
    void getInstance() {
        CacheManager newCacheManager = CacheManager.getInstance();
        assertEquals(newCacheManager, cacheManager);
    }
}