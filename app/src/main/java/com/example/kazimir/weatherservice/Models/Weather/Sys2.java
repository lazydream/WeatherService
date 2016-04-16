package com.example.kazimir.weatherservice.Models.weather;

/**
 * Created by Kazimir on 15.04.2016.
 */
public class Sys2 {
    private String pod;

    public Sys2(String pod) {
        this.pod = pod;
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }
}
