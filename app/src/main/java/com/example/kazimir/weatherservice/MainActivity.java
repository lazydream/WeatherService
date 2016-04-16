package com.example.kazimir.weatherservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kazimir.weatherservice.AsyncTasks.WeatherTask;
import com.example.kazimir.weatherservice.Models.weather.WeatherData;
import com.example.kazimir.weatherservice.Services.WeatherService;
import com.orm.SugarContext;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {
    WeatherTask task;
    Realm realm;
    private RealmConfiguration realmConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configuring Realm
        realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);

        Intent intent = new Intent(getApplicationContext(), WeatherService.class);
        startService(intent);
    }
}
