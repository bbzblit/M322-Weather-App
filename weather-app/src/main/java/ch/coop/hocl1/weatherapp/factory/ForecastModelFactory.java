package ch.coop.hocl1.weatherapp.factory;

import ch.coop.hocl1.weatherapp.models.openweather.ForecastModel;
import com.google.gson.JsonObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ForecastModelFactory {

    private ForecastModelFactory() {
        // prevent instance creation of factories
    }

    public static ForecastModel create(JsonObject entry) {
        double temperature = entry.get("main").getAsJsonObject().get("temp").getAsDouble();
        int humidity = entry.get("main").getAsJsonObject().get("humidity").getAsInt();
        String description = entry.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main").getAsString();
        LocalDateTime entryDateTime = parseStringToLocalDateTime(entry.get("dt_txt").getAsString());

        ForecastModel forecastModel = new ForecastModel();
        forecastModel.setTemperature(temperature);
        forecastModel.setHumidity(humidity);
        forecastModel.setDescription(description);
        forecastModel.setDate(entryDateTime);

        return forecastModel;
    }

    private static LocalDateTime parseStringToLocalDateTime(String dateTimeAsString) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateTimeAsString, dateTimeFormatter);
    }
}
