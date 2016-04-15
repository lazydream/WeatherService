package com.example.kazimir.weatherservice.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.kazimir.weatherservice.Models.Weather.City;
import com.example.kazimir.weatherservice.Models.Weather.Clouds;
import com.example.kazimir.weatherservice.Models.Weather.Coord;
import com.example.kazimir.weatherservice.Models.Weather.List;
import com.example.kazimir.weatherservice.Models.Weather.Main;
import com.example.kazimir.weatherservice.Models.Weather.Rain;
import com.example.kazimir.weatherservice.Models.Weather.Sys;
import com.example.kazimir.weatherservice.Models.Weather.Sys2;
import com.example.kazimir.weatherservice.Models.Weather.Weather;
import com.example.kazimir.weatherservice.Models.Weather.WeatherRecord;
import com.example.kazimir.weatherservice.Models.Weather.Wind;
import com.google.common.base.Strings;
import com.google.common.io.CharStreams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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

            //Очистили БД от предыдущих запросов

            //Получили весь JSON
            JSONObject jsonObject = new JSONObject(response);

            //Получили объект City
            JSONObject cityObject = jsonObject.getJSONObject("city");
            int cityId = cityObject.getInt("id");
            String cityName = cityObject.getString("name");

            //Получили объект Coord
            JSONObject coordObject = cityObject.getJSONObject("coord");
            double lon = coordObject.getDouble("lon");
            double lat = coordObject.getDouble("lat");

            //Создали Coord
            Coord coord = new Coord(lon, lat);
            String country = cityObject.getString("country");
            int population = cityObject.getInt("population");

            //Получили объект Sys в City
            JSONObject sysInCity = cityObject.getJSONObject("sys");
            int populationInSys = sysInCity.getInt("population");

            //Создали объект Sys
            Sys sys = new Sys(populationInSys);

            //Создали объект City
            City city = new City(cityId, cityName, coord, country, population, sys);

            String cod = jsonObject.getString("cod");
            double message = jsonObject.getDouble("message");
            int cnt = jsonObject.getInt("cnt");

            //Далее идет работа со списком прогнозов
            JSONArray weatherArray = jsonObject.getJSONArray("list");
            ArrayList<List> weatherList = new ArrayList<>();
            for (int i=0; i<weatherArray.length(); i++){
                JSONObject object = weatherArray.getJSONObject(i);
                int dt = object.getInt("dt");

                JSONObject mainObject = object.getJSONObject("main");
                double temp = mainObject.getDouble("temp");
                double temp_min = mainObject.getDouble("temp_min");
                double temp_max = mainObject.getDouble("temp_max");
                double pressure = mainObject.getDouble("pressure");
                double sea_level = mainObject.getDouble("sea_level");
                double grnd_level = mainObject.getDouble("grnd_level");
                int humidity = mainObject.getInt("humidity");
                double temp_kf = mainObject.getDouble("temp_kf");

                Main main = new Main(temp, temp_min, pressure, temp_max, sea_level, grnd_level, humidity, temp_kf);

                JSONArray weatherinweatgerArray = object.getJSONArray("weather");
                ArrayList<Weather> wwArray = new ArrayList<>();
                for (int j=0;j<weatherinweatgerArray.length(); j++) {
                    JSONObject wwObject = weatherinweatgerArray.getJSONObject(i);
                    int id = wwObject.getInt("id");
                    String mainString = wwObject.getString("main");
                    String description = wwObject.getString("description");
                    String icon = wwObject.getString("icon");
                    Weather wWeather = new Weather(id, mainString, description, icon);
                    wwArray.add(wWeather);
                }

                JSONObject cloudsObject = object.getJSONObject("clouds");
                int all = cloudsObject.getInt("all");
                Clouds clouds = new Clouds(all);

                JSONObject windObject = object.getJSONObject("wind");
                double speed = windObject.getDouble("speed");
                double deg = windObject.getDouble("deg");
                Wind wind = new Wind(speed, deg);

                JSONObject rainObject = object.getJSONObject("rain");
                double rainVal = rainObject.getDouble("3h");
                Rain rain = new Rain(rainVal);

                JSONObject wwSysObject = object.getJSONObject("sys");
                String date = wwSysObject.getString("dt_txt");
                Sys2 sys2 = new Sys2(date);

                List newListMember = new List(dt, main, wwArray, clouds, wind, rain, sys2, date);
                weatherList.add(newListMember);
                Log.d("Database", "New label added");
            }

            WeatherRecord record = new WeatherRecord(city, cod, message, cnt, weatherList);
            record.save();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
