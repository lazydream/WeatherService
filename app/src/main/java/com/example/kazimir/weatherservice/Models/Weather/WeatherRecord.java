package com.example.kazimir.weatherservice.Models.Weather;

import com.orm.SugarRecord;

import java.util.ArrayList;

/**
 * Created by Kazimir on 15.04.2016.
 */

public class WeatherRecord extends SugarRecord {
    private double temp;
    private double temp_min;
    private double temp_max;
    private double pressure;
    private double windSpeed;
    private String city;
    private String weatherMain;
    private String getWeatherDescription;
    private String dateAndTime;

    public WeatherRecord(double temp, double temp_min, double temp_max, double windSpeed, double pressure, String city, String weatherMain, String getWeatherDescription, String dateAndTime) {
        this.temp = temp;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
        this.city = city;
        this.weatherMain = weatherMain;
        this.getWeatherDescription = getWeatherDescription;
        this.dateAndTime = dateAndTime;
    }

    public double getTemp() {
        return temp;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public double getPressure() {
        return pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getCity() {
        return city;
    }

    public String getWeatherMain() {
        return weatherMain;
    }

    public String getGetWeatherDescription() {
        return getWeatherDescription;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public WeatherRecord() {
    }
}