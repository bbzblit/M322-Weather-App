package ch.coop.hocl1.weatherapp.dao;

import ch.coop.hocl1.weatherapp.models.geocoder.GeoLocation;

import java.util.List;

public interface GeocoderDao {

    List<GeoLocation> readGeoLocation(String query);

}
