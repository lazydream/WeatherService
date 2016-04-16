package com.example.kazimir.weatherservice.Models.weather;

/**
 * Created by Kazimir on 15.04.2016.
 */
public class Sys {

    private int population;

    public Sys(int population) {
        this.population = population;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
