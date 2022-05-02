package pt.ua.deti.tqs.hw1.project_api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Generated;

import java.util.Date;

@Generated
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecentData {

    @JsonProperty("id")
    private String id;

    @JsonProperty("symbol")
    private String iso;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Continent")
    private String continent;

    @JsonProperty("date")
    private Date date;

    @JsonProperty("total_cases")
    private int totalCases;

    @JsonProperty("new_cases")
    private int newCases;

    @JsonProperty("total_deaths")
    private int totalDeaths;

    @JsonProperty("new_deaths")
    private int newDeaths;

    @JsonProperty("total_tests")
    private int totalTests;

    @JsonProperty("new_tests")
    private int newTests;
}
