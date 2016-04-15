package com.example.kazimir.weatherservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.kazimir.weatherservice.AsyncTasks.WeatherTask;
import com.orm.SugarContext;

public class MainActivity extends AppCompatActivity {
    WeatherTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SugarContext.init(this);
        task = new WeatherTask();
        task.execute(this);
    }
}
