package com.example.kazimir.weatherservice.Models.weather;

/**
 * Created by Kazimir on 15.04.2016.
 */
public class City {
    private int id;
    private String name;
    private Coord coord;
    private String Country;
    private int population;
    private Sys sys;

    public City(int id, String name, Coord coord, String country, int population, Sys sys) {
        this.id = id;
        this.name = name;
        this.coord = coord;
        Country = country;
        this.population = population;
        this.sys = sys;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
