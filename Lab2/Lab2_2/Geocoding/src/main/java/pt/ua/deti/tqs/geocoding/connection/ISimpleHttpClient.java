package pt.ua.deti.tqs.geocoding.connection;

import java.io.IOException;

public interface ISimpleHttpClient {
    String doHttpGet(String url) throws IOException;
}
