package ch.coop.hocl1.weatherapp.models.geocoder;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class GeoLocation {

    private String name;
    private double lat;
    private double lon;
    private String country;
    private String state;

}
