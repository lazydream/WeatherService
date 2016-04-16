package com.example.kazimir.weatherservice.Models.weather;

import io.realm.RealmObject;

/**
 * Created by Kazimir on 15.04.2016.
 */

public class WeatherData extends RealmObject {
    private double temp;
    private double temp_min;
    private double temp_max;
    private double pressure;
    private double windSpeed;
    private String city;
    private String weatherMain;
    private String weatherDescription;
    private String dateAndTime;

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public void setWeatherMain(String weatherMain) {
        this.weatherMain = weatherMain;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public WeatherData() {
    }

    public WeatherData(double temp, double temp_min, double temp_max, double windSpeed, double pressure, String city, String weatherMain, String WeatherDescription, String dateAndTime) {
        this.temp = temp;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.windSpeed = windSpeed;
        this.pressure = pressure;
        this.city = city;
        this.weatherMain = weatherMain;
        this.weatherDescription = WeatherDescription;
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

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }
}