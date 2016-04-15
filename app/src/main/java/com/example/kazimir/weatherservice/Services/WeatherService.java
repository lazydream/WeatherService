package com.example.kazimir.weatherservice.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherService extends Service {
    public WeatherService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try{
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/city?id=524901&APPID=f8da2500f4b2e7de706cac63323ac431");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.connect();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
