package pt.ua.deti.tqs.hw1.project_api.config;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("classpath:application.properties")
public class HttpClientConfig {

    private static final int CONNECT_TIMEOUT = Integer.getInteger("HTTP_CONNECT_TIMEOUT", 10_000);
    private static final int REQUEST_TIMEOUT = Integer.getInteger("HTTP_REQUEST_TIMEOUT", 30_000);
    private static final int SOCKET_TIMEOUT = Integer.getInteger("HTTP_REQUEST_TIMEOUT", REQUEST_TIMEOUT);

    @Value("${api.host}")
    private String host;

    @Value("${api.host.key}")
    private String key;

    @Bean
    public CloseableHttpClient httpClient() {

        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("X-RapidAPI-Host", host));
        headers.add(new BasicHeader("X-RapidAPI-Key", key));
        headers.add(new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"));

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(REQUEST_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();

        return HttpClients.custom().setDefaultRequestConfig(requestConfig)
                .setDefaultHeaders(headers)
                .build();
    }

}
