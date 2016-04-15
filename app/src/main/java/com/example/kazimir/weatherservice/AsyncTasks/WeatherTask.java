package com.example.kazimir.weatherservice.AsyncTasks;

import android.os.AsyncTask;

import com.google.common.io.CharStreams;

import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Kazimir on 15.04.2016.
 */
public class WeatherTask extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] params) {
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/city?id=524901&APPID=f8da2500f4b2e7de706cac63323ac431");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            String response = CharStreams.toString(new InputStreamReader(connection.getInputStream()));
            JSONObject jsonObject = new JSONObject(response);
            JSONObject cityObject = jsonObject.getJSONObject("city");
            int cityId = cityObject.getInt("id");
            String cityName = cityObject.getString("name");
            JSONObject coordObject = cityObject.getJSONObject("coord");
            double lon = coordObject.getDouble("lon");
            double lat = coordObject.getDouble("lat");




        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
