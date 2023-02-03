package ch.coop.hocl1.weatherapp.rest.controller;

import ch.coop.hocl1.weatherapp.dao.GeocoderDao;
import ch.coop.hocl1.weatherapp.models.geocoder.GeoLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/geo")
public class GeoController {

    @Autowired
    private GeocoderDao geocoderDao;

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/search/location",
            produces = {"application/json", "text/html"}
    )
    public ResponseEntity<List<GeoLocation>> readLocation(String query) {
        List<GeoLocation> searchResult = geocoderDao.readGeoLocation(query);

        return new ResponseEntity<>(searchResult, HttpStatus.OK);
    }
}


