package com.example.kazimir.weatherservice.AsyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import com.example.kazimir.weatherservice.MainActivity;
import com.example.kazimir.weatherservice.Models.weather.City;
import com.example.kazimir.weatherservice.Models.weather.Clouds;
import com.example.kazimir.weatherservice.Models.weather.Coord;
import com.example.kazimir.weatherservice.Models.weather.List;
import com.example.kazimir.weatherservice.Models.weather.Main;
import com.example.kazimir.weatherservice.Models.weather.Rain;
import com.example.kazimir.weatherservice.Models.weather.Sys;
import com.example.kazimir.weatherservice.Models.weather.Sys2;
import com.example.kazimir.weatherservice.Models.weather.Weather;
import com.example.kazimir.weatherservice.Models.weather.WeatherData;
import com.example.kazimir.weatherservice.Models.weather.Wind;
import com.google.common.io.CharStreams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by Kazimir on 15.04.2016.
 */
public class WeatherTask extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] params) {
        Realm realm = null;
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/city?id=524901&APPID=f8da2500f4b2e7de706cac63323ac431");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            String response = CharStreams.toString(new InputStreamReader(connection.getInputStream()));

            //Очистили БД от предыдущих запросов
            realm = Realm.getDefaultInstance();
            RealmQuery<WeatherData> query = realm.where(WeatherData.class);
            RealmResults<WeatherData> result = query.findAll();
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
            RealmResults newResult = realm.where(WeatherData.class)
                    .findAll();

            //Получили весь JSON
            JSONObject jsonObject = new JSONObject(response);

            //Получили объект City
            JSONObject cityObject = jsonObject.getJSONObject("city");
            int cityId = cityObject.getInt("id");
            final String cityName = cityObject.getString("name");

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
                final double temp = mainObject.getDouble("temp");
                final double temp_min = mainObject.getDouble("temp_min");
                final double temp_max = mainObject.getDouble("temp_max");
                final double pressure = mainObject.getDouble("pressure");
                double sea_level = mainObject.getDouble("sea_level");
                double grnd_level = mainObject.getDouble("grnd_level");
                int humidity = mainObject.getInt("humidity");
                double temp_kf = mainObject.getDouble("temp_kf");

                Main main = new Main(temp, temp_min, pressure, temp_max, sea_level, grnd_level, humidity, temp_kf);

                JSONArray weatherinweatgerArray = object.getJSONArray("weather");
                final ArrayList<Weather> wwArray = new ArrayList<>();
                for (int j=0; j<weatherinweatgerArray.length(); j++) {
                    JSONObject wwObject = weatherinweatgerArray.getJSONObject(j);
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
                final double speed = windObject.getDouble("speed");
                double deg = windObject.getDouble("deg");
                Wind wind = new Wind(speed, deg);

               // JSONObject rainObject = object.getJSONObject("rain");
                double rainVal;
                //if (rainObject.has("3h")) {
                //    rainVal = rainObject.getDouble("3h");
                //} else {
                    rainVal =0;
                //}
                Rain rain = new Rain(rainVal);

                JSONObject wwSysObject = object.getJSONObject("sys");
                String pod = wwSysObject.getString("pod");
                Sys2 sys2 = new Sys2(pod);

                final String date = object.getString("dt_txt");

                List newListMember = new List(dt, main, wwArray, clouds, wind, rain, sys2, date);
                weatherList.add(newListMember);
                WeatherData record = new WeatherData(temp, temp_min, temp_max, speed, pressure, cityName, wwArray.get(0).getMain(), wwArray.get(0).getDescription(), date);

                //Сохранение записей в Realm
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        WeatherData weatherData = realm.createObject(WeatherData.class);
                        weatherData.setTemp(temp);
                        weatherData.setTemp_min(temp_min);
                        weatherData.setTemp_max(temp_max);
                        weatherData.setWindSpeed(speed);
                        weatherData.setPressure(pressure);
                        weatherData.setCity(cityName);
                        weatherData.setWeatherMain(wwArray.get(0).getMain());
                        weatherData.setWeatherDescription(wwArray.get(0).getDescription());
                        weatherData.setDateAndTime(date);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
        return null;
    }
}
