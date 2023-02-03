package ch.coop.hocl1.weatherapp.models.openweather;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ForecastModel {

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;
    private String description;
    private double temperature;
    private int humidity;



}
