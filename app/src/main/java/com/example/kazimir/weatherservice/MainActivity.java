package com.example.kazimir.weatherservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.kazimir.weatherservice.Adapters.WeatherAdapter;
import com.example.kazimir.weatherservice.AsyncTasks.WeatherTask;
import com.example.kazimir.weatherservice.Models.weather.WeatherData;
import com.example.kazimir.weatherservice.Services.WeatherService;
import com.orm.SugarContext;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmResults;
import io.realm.RealmSchema;

public class MainActivity extends AppCompatActivity {

    ListView listAdapterView;
    WeatherAdapter weatherAdapter;
    Realm realm;
    private RealmConfiguration realmConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);

        initAdapter();

        BroadcastReceiver reciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("Reciever", "Уведомление получено");
                updateAdapter();
            }
        };
        IntentFilter filter = new IntentFilter("Action");
        registerReceiver(reciever, filter);

        Intent intent = new Intent(getApplicationContext(), WeatherService.class);
        startService(intent);
    }

    private void initAdapter() {
        realm = Realm.getDefaultInstance();
        RealmResults<WeatherData> weatherDatas = realm.where(WeatherData.class).findAll();

        listAdapterView = (ListView) findViewById(R.id.lv_adapter);
        weatherAdapter = new WeatherAdapter(this, R.layout.weather_item, weatherDatas);
        listAdapterView.setAdapter(weatherAdapter);
    }

    private void updateAdapter() {
        weatherAdapter.notifyDataSetChanged();
    }
}
