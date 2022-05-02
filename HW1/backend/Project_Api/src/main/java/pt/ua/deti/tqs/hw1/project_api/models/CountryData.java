package pt.ua.deti.tqs.hw1.project_api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryData {

    @JsonProperty("id")
    private String id;

    @JsonProperty("rank")
    private int rank;

    @JsonProperty("ThreeLetterSymbol")
    private String iso;

    @JsonProperty("Country")
    private String country;

    @JsonProperty("Continent")
    private String continent;

    @JsonProperty("Infection_Risk")
    private double infectionRisk;

    @JsonProperty("Case_Fatality_Rate")
    private double caseFatalityRate;

    @JsonProperty("Recovery_Proporation")
    private double recoveryProportion;

    @JsonProperty("TotalCases")
    private int totalCases;

    @JsonProperty("NewCases")
    private int newCases;

    @JsonProperty("TotalDeaths")
    private int totalDeaths;

    @JsonProperty("TotalRecovered")
    private int totalRecovered;

    @JsonProperty("NewRecovered")
    private int newRecovered;

    @JsonProperty("ActiveCases")
    private int activeCases;

    @JsonProperty("TotalTests")
    private int totalTests;

    @JsonProperty("Population")
    private int population;

    @JsonProperty("Tests_1M_Pop")
    private int tests1M;

    @JsonProperty("TotCases_1M_Pop")
    private int cases1M;

}