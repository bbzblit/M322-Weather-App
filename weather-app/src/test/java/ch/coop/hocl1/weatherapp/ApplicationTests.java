package ch.coop.hocl1.weatherapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class ApplicationTests {

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<Object>) (json, type, jsonDeserializationContext)
                    -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")))
            .create();

    @Test
    void contextLoads() {
    }

    @Test
    void positiveTest_gsonDates() {
        String something = "{}";
    }

}
