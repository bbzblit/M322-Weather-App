package ch.coop.hocl1.weatherapp.dao.impl;

import ch.coop.hocl1.weatherapp.dao.GeocoderDao;
import ch.coop.hocl1.weatherapp.models.geocoder.GeoLocation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
public class GeocoderDaoImpl implements GeocoderDao {

    private WebClient webClient;

    private String apiKey;

    @Autowired
    private Environment environment;

    @PostConstruct
    public void initWebClient() {
        apiKey = environment.getProperty("weathermap.api.key");

        String baseUrl = environment.getProperty("geocoder.base.url");

        HttpClient nettyHttpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(1))
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
    public List<GeoLocation> readGeoLocation(String query) {
        MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
        queryParameters.put("appid", Collections.singletonList(apiKey));
        queryParameters.put("limit", Collections.singletonList("3"));
        queryParameters.put("q", Collections.singletonList(query));

        String responseAsString = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/direct").queryParams(queryParameters).build())
                .retrieve().bodyToMono(String.class).block();

        Gson gson = new Gson();

        return gson.fromJson(responseAsString, TypeToken.getParameterized(List.class, GeoLocation.class).getType());
    }
}
