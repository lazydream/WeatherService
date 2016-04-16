package com.example.kazimir.weatherservice.Models.weather;

/**
 * Created by Kazimir on 15.04.2016.
 */
public class Coord {
    private double lon;
    private double lat;

    public Coord(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }
}

