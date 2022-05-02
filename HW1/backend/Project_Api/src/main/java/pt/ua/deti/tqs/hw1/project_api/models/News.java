package pt.ua.deti.tqs.hw1.project_api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;

@Generated
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class News {

    @JsonProperty("news_id")
    private final String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("link")
    private String link;

    @JsonProperty("urlToImage")
    private String urlToImage;

    @JsonProperty("pubDate")
    private String pubDate;

    @JsonProperty("content")
    private String content;

    @JsonProperty("reference")
    private String reference;

}
