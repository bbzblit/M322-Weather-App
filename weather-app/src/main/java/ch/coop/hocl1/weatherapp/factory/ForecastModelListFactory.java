package ch.coop.hocl1.weatherapp.factory;

import ch.coop.hocl1.weatherapp.models.openweather.ForecastModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public final class ForecastModelListFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForecastModelListFactory.class);

    private ForecastModelListFactory() {
        // prevent instance creation of factories
    }

    public static List<ForecastModel> create(String forecastResponseAsString) {
        List<ForecastModel> results = new ArrayList<>();

        try {
            JsonObject responseAsJson = JsonParser.parseString(forecastResponseAsString).getAsJsonObject();
            JsonArray list = responseAsJson.getAsJsonArray("list");

            for (JsonElement entry : list) {
                results.add(ForecastModelFactory.create(entry.getAsJsonObject()));
            }
        } catch (JsonSyntaxException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }

        return results;
    }
}
