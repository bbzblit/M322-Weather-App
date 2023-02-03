package ch.coop.hocl1.weatherapp.rest.controller;

import ch.coop.hocl1.weatherapp.dao.OpenWeatherDao;
import ch.coop.hocl1.weatherapp.models.geocoder.GeoLocation;
import ch.coop.hocl1.weatherapp.models.openweather.CurrentWeatherModel;
import ch.coop.hocl1.weatherapp.models.openweather.ForecastModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/weather")
public class OpenWeatherController {

    @Autowired
    private OpenWeatherDao openWeatherDao;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/forecast",
            produces = { "application/json", "text/html"}
    )
    public ResponseEntity<List<ForecastModel>> readForecast(double latitude, double longitude,  String clockTime) {
        LocalTime clockTimeParam = LocalTime.parse(clockTime, DateTimeFormatter.ofPattern("HH:mm:ss"));

        List<ForecastModel> searchResult = openWeatherDao.readForecast(latitude, longitude);
        List<ForecastModel> filteredResults = new ArrayList<>();

        for (ForecastModel entry : searchResult) {
            if (Duration.between(entry.getDate().toLocalTime(), clockTimeParam).toHours() == 0) {
                filteredResults.add(entry);
            }
        }

        return new ResponseEntity<>(filteredResults, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/current-weather",
            produces = { "application/json", "text/html"}
    )
    public ResponseEntity<CurrentWeatherModel> readCurrentWeather(double latitude, double longitude) {
        CurrentWeatherModel searchResult = openWeatherDao.readCurrentWeather(latitude, longitude);

        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }

}
