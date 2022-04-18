package pt.ua.deti.tqs.hw1.project_api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StateData {

    @JsonProperty("name")
    private String country;

    @JsonProperty("province")
    private String province;

    @JsonProperty("iso")
    private String iso;

    @JsonProperty("date")
    private Date date;

    @JsonProperty("confirmed")
    private int confirmed;

    @JsonProperty("recovered")
    private int recovered;

    @JsonProperty("deaths")
    private int deaths;

    @JsonProperty("Case_Fatality_Rate")
    private double caseFatalityRate;

    @JsonProperty("active")
    private int active;

    @JsonProperty("fatality_rate")
    private double fatalityRate;

    @JsonProperty("Recovery_Proporation")
    private double recoveryProportion;
}
