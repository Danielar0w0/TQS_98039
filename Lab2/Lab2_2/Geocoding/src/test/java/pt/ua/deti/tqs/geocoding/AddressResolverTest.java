package pt.ua.deti.tqs.geocoding;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.ua.deti.tqs.geocoding.connection.ISimpleHttpClient;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddressResolverTest {

    @Mock
    private ISimpleHttpClient httpClient;

    private AddressResolver resolver;

    @BeforeEach
    void setUp() {
        resolver = new AddressResolver(httpClient);
    }

    @Test
    void findAddressForLocation() throws ParseException, IOException, URISyntaxException {

        // Coordinates
        double latitude = 40.640661;
        double longitude = -8.656688;

        // Set mock expectations
        String response = "{\"info\":{\"statuscode\":0,\"copyright\":{\"text\":\"\\u00A9 2022 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"\\u00A9 2022 MapQuest, Inc.\"},\"messages\":[]},\"options\":{\"maxResults\":1,\"thumbMaps\":true,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{\"latLng\":{\"lat\":40.640661,\"lng\":-8.656688}},\"locations\":[{\"street\":\"Cais do Alboi\",\"adminArea6\":\"\",\"adminArea6Type\":\"Neighborhood\",\"adminArea5\":\"Gl\\u00F3ria e Vera Cruz\",\"adminArea5Type\":\"City\",\"adminArea4\":\"\",\"adminArea4Type\":\"County\",\"adminArea3\":\"Centro\",\"adminArea3Type\":\"State\",\"adminArea1\":\"PT\",\"adminArea1Type\":\"Country\",\"postalCode\":\"3800-246\",\"geocodeQualityCode\":\"B1AAA\",\"geocodeQuality\":\"STREET\",\"dragPoint\":false,\"sideOfStreet\":\"N\",\"linkId\":\"0\",\"unknownInput\":\"\",\"type\":\"s\",\"latLng\":{\"lat\":40.640524,\"lng\":-8.656713},\"displayLatLng\":{\"lat\":40.640524,\"lng\":-8.656713},\"mapUrl\":\"http://open.mapquestapi.com/staticmap/v5/map?key=uXSAVwYWbf9tJmsjEGHKKAo0gOjZfBLQ&type=map&size=225,160&locations=40.64052368145179,-8.656712986761146|marker-sm-50318A-1&scalebar=true&zoom=15&rand=-628639059\",\"roadMetadata\":null}]}]}";
        Mockito.when(httpClient.doHttpGet(Mockito.anyString())).thenReturn(response);

        // Test
        Address result = resolver.findAddressForLocation(latitude, longitude);
        Address expected = new Address("Cais do Alboi", "Glória e Vera Cruz", "Centro", "3800-246", null);

        assertEquals(expected, result);
        Mockito.verify(httpClient).doHttpGet(Mockito.anyString());
    }

    @Test
    void findAddressForBadCoordinates() throws ParseException, IOException, URISyntaxException {

        // Bad coordinates
        double latitude = 40;
        double longitude = -80000;

        // Set mock expectations
        Mockito.when(httpClient.doHttpGet(Mockito.anyString())).thenReturn("{\"results\":[{\"locations\": []}]}");

        assertThrows(IndexOutOfBoundsException.class, () -> resolver.findAddressForLocation(latitude, longitude));
        Mockito.verify(httpClient).doHttpGet(Mockito.anyString());
    }

    @Test
    void findAddressForNoCoordinates() throws ParseException, IOException, URISyntaxException {

        // No coordinates
        Double latitude = null;
        Double longitude = null;

        assertThrows(NullPointerException.class, () -> resolver.findAddressForLocation(latitude, longitude));
    }

}