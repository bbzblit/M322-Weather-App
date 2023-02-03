package ch.coop.hocl1.weatherapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RequestController {

    @RequestMapping("/home")
    public String home() {
        return "index";
    }

    @RequestMapping("/forecast")
    public String forecast() {
        return "forecast";
    }

    @RequestMapping("/weather")
    public String weather() {
        return "weather";
    }
}
