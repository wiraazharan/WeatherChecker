package com.magic.wiraazharan.openweathermaprest;

/**
 * Created by wiraazharan on 7/18/15.
 */
public class WeatherData {

    private String winds;
    private String temperaturee;
    private String weather;

    public WeatherData(String weather, String winds, String temperaturee) {
        this.weather = weather;
        this.winds = winds;
        this.temperaturee = temperaturee;
    }


}
