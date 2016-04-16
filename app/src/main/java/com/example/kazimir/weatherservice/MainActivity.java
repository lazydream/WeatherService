package com.example.kazimir.weatherservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kazimir.weatherservice.AsyncTasks.WeatherTask;
import com.orm.SugarContext;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {
    WeatherTask task;
    private RealmConfiguration realmConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configuring Realm
        realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);

        task = new WeatherTask();
        task.execute(this);
    }
}
