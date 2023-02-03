package ch.coop.hocl1.weatherapp.dao.impl;

import ch.coop.hocl1.weatherapp.dao.OpenWeatherDao;
import ch.coop.hocl1.weatherapp.factory.CurrentWeatherModelFactory;
import ch.coop.hocl1.weatherapp.factory.ForecastModelListFactory;
import ch.coop.hocl1.weatherapp.models.openweather.CurrentWeatherModel;
import ch.coop.hocl1.weatherapp.models.openweather.ForecastModel;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Service
public class OpenWeatherDaoImpl implements OpenWeatherDao {

    private WebClient webClient;

    private String apiKey;

    @Autowired
    private Environment environment;

    @PostConstruct
    public void initWebClient() {
        apiKey = environment.getProperty("weathermap.api.key");

        String baseUrl = environment.getProperty("openweather.base.url");

        // TODO you can switch to any HttpClient annotation that you want
        HttpClient nettyHttpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(3))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000);

        this.webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(nettyHttpClient))
                .baseUrl(baseUrl)
                .defaultHeaders(header -> {
                    header.setAcceptCharset(Collections.singletonList(StandardCharsets.UTF_8));
                })
                .build();
    }

    @Override
    public List<ForecastModel> readForecast(double latitude, double longitude) {
        MultiValueMap<String, String> queryParameters = buildQueryParameters(latitude, longitude);

        String responseAsString = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/forecast").queryParams(queryParameters).build())
                .retrieve().bodyToMono(String.class).block();

        return ForecastModelListFactory.create(responseAsString);
    }

    @Override
    public CurrentWeatherModel readCurrentWeather(double latitude, double longitude) {
        MultiValueMap<String, String> queryParameters = buildQueryParameters(latitude, longitude);

        String responseAsString = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/weather").queryParams(queryParameters).build())
                .retrieve().bodyToMono(String.class).block();

        return CurrentWeatherModelFactory.create(responseAsString);
    }

    private MultiValueMap<String, String> buildQueryParameters(double latitude, double longitude) {
        MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
        queryParameters.put("appid", Collections.singletonList(apiKey));
        queryParameters.put("units", Collections.singletonList("metric"));
        queryParameters.put("lat", Collections.singletonList(String.valueOf(latitude)));
        queryParameters.put("lon", Collections.singletonList(String.valueOf(longitude)));

        return queryParameters;
    }

}
