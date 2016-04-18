package com.example.kazimir.weatherservice.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kazimir.weatherservice.Models.weather.Weather;
import com.example.kazimir.weatherservice.Models.weather.WeatherData;
import com.example.kazimir.weatherservice.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Kazimir on 17.04.2016.
 */
public class WeatherAdapter extends ArrayAdapter<WeatherData> {
    Activity context;
    private  RealmConfiguration realmConfiguration;
    private int resource;
    RealmResults<WeatherData> weatherDatas;

    public WeatherAdapter(Context context, int resource, RealmResults<WeatherData> weatherDatas) {
        super(context, resource, weatherDatas);
        this.resource = resource;
        this.context = (Activity) context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Realm realm = Realm.getDefaultInstance();
        this.weatherDatas = realm.where(WeatherData.class).findAll();
        realm.close();

        LayoutInflater inflater = context.getLayoutInflater();
        View templateView = inflater.inflate(R.layout.weather_item, null);
        TextView dateTextView = (TextView) templateView.findViewById(R.id.tv_date);
        TextView weatherMain = (TextView) templateView.findViewById(R.id.tv_weather_main);
        TextView weatherDescription = (TextView) templateView.findViewById(R.id.weather_description);
        TextView currentTemp = (TextView) templateView.findViewById(R.id.tv_current_temp);
        TextView minTemp = (TextView) templateView.findViewById(R.id.tv_min_temp);
        TextView maxTemp = (TextView) templateView.findViewById(R.id.tv_max_temp);

        dateTextView.setText(weatherDatas.get(position).getDateAndTime());
        weatherMain.setText(weatherDatas.get(position).getWeatherMain());
        weatherDescription.setText(weatherDatas.get(position).getWeatherDescription());
        currentTemp.setText(String.valueOf(Math.round(weatherDatas.get(position).getTemp() - 273)) + "°C");
        minTemp.setText(String.valueOf(Math.round(weatherDatas.get(position).getTemp_min() - 273)) + "°C");
        maxTemp.setText(String.valueOf(Math.round(weatherDatas.get(position).getTemp_max() - 273)) + "°C");

        return  templateView;
    }
}
