package ch.coop.hocl1.weatherapp.models.openweather;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CurrentWeatherModel {

    private double temperature;
    @JsonProperty("feels_like")
    private double feelsLike;
    @JsonProperty("wind_speed")
    private double windSpeed;
    private int humidity;
    private String description;

}
