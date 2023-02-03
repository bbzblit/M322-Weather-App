package ch.coop.hocl1.weatherapp.factory;

import ch.coop.hocl1.weatherapp.exception.types.BackendDataInvalidException;
import ch.coop.hocl1.weatherapp.models.openweather.CurrentWeatherModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public final class CurrentWeatherModelFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentWeatherModel.class);

    private CurrentWeatherModelFactory() {
        // prevent instance creation of factories
    }

    public static CurrentWeatherModel create(String responseAsString) {
        try {
            JsonObject entry = JsonParser.parseString(responseAsString).getAsJsonObject();

            double temperature = entry.get("main").getAsJsonObject().get("temp").getAsDouble();
            double feelsLike = entry.get("main").getAsJsonObject().get("feels_like").getAsDouble();
            double windSpeed = entry.get("wind").getAsJsonObject().get("speed").getAsDouble();
            int humidity = entry.get("main").getAsJsonObject().get("humidity").getAsInt();
            String description = entry.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main").getAsString();

            CurrentWeatherModel weatherModel = new CurrentWeatherModel();
            weatherModel.setTemperature(temperature);
            weatherModel.setFeelsLike(feelsLike);
            weatherModel.setWindSpeed(windSpeed);
            weatherModel.setHumidity(humidity);
            weatherModel.setDescription(description);

            return weatherModel;

        } catch (JsonSyntaxException e) {
            throw new BackendDataInvalidException(e);
        }
    }
}
