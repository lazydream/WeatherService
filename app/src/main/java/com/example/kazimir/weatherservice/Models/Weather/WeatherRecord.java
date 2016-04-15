package com.example.kazimir.weatherservice.Models.Weather;

import com.orm.SugarRecord;

import java.util.ArrayList;

/**
 * Created by Kazimir on 15.04.2016.
 */

public class WeatherRecord extends SugarRecord {
    private City city;
    private String cod;
    private double message;
    private int cnt;
    private ArrayList <List> list;

    public WeatherRecord() {
    }

    public WeatherRecord(City city, String cod, double message, int cnt, ArrayList<List> list) {
        this.city = city;
        this.cod = cod;
        this.message = message;
        this.cnt = cnt;
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public ArrayList<List> getList() {
        return list;
    }

    public void setList(ArrayList<List> list) {
        this.list = list;
    }
}