package pt.ua.deti.tqs.hw1.project_api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;

@Generated
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Country {

    @JsonProperty("Country")
    private String name;

    @JsonProperty("ThreeLetterSymbol")
    private String iso;
}
