package com.example.kazimir.weatherservice.Models.weather;

/**
 * Created by Kazimir on 15.04.2016.
 */
public class Rain {
    private double rainValue;

    public Rain(double rainValue) {
        this.rainValue = rainValue;
    }

    public double getRainValue() {
        return rainValue;
    }

    public void setRainValue(double rainValue) {
        this.rainValue = rainValue;
    }
}

