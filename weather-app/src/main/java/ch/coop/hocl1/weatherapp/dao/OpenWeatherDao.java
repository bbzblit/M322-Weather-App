package ch.coop.hocl1.weatherapp.dao;

import ch.coop.hocl1.weatherapp.models.openweather.CurrentWeatherModel;
import ch.coop.hocl1.weatherapp.models.openweather.ForecastModel;

import java.time.LocalDateTime;
import java.util.List;

public interface OpenWeatherDao {

    List<ForecastModel> readForecast(double latitude, double longitude);

    CurrentWeatherModel readCurrentWeather(double latitude, double longitude);

}
