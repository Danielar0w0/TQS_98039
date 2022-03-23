package pt.ua.deti.tqs.integration;

import pt.ua.deti.tqs.geocoding.Address;
import pt.ua.deti.tqs.geocoding.AddressResolver;
import pt.ua.deti.tqs.geocoding.connection.ISimpleHttpClient;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ua.deti.tqs.geocoding.connection.TqsBasicHttpClient;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddressResolverIT {

    private ISimpleHttpClient httpClient;
    private AddressResolver resolver;

    @BeforeEach
    void setUp() {
        httpClient = new TqsBasicHttpClient();
        resolver = new AddressResolver(httpClient);
    }

    @Test
    void findAddressForLocation() throws ParseException, IOException, URISyntaxException {

        // Coordinates
        double latitude = 40.640661;
        double longitude = -8.656688;

        Address result = resolver.findAddressForLocation(latitude, longitude);
        Address expected = new Address("Cais do Alboi", "Glória e Vera Cruz", "Centro", "3800-246", null);

        assertEquals(expected, result);
    }

    @Test
    void findAddressForNoCoordinates() throws ParseException, IOException, URISyntaxException {

        // No Coordinates
        Double latitude = null;
        Double longitude = null;

        assertThrows(NullPointerException.class, () -> resolver.findAddressForLocation(latitude, longitude));
    }

    @Test
    void findAddressForBadCoordinates() throws ParseException, IOException, URISyntaxException {

        // Bad Coordinates
        double latitude = 40;
        double longitude = -80000;

        assertThrows(IndexOutOfBoundsException.class, () -> resolver.findAddressForLocation(latitude, longitude));
    }
}