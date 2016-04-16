package com.example.kazimir.weatherservice.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.kazimir.weatherservice.AsyncTasks.WeatherTask;
import com.example.kazimir.weatherservice.MainActivity;
import com.example.kazimir.weatherservice.Models.weather.WeatherData;
import com.example.kazimir.weatherservice.R;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import io.realm.Realm;
import io.realm.RealmQuery;

public class WeatherService extends Service {
    private static final int CURRENT_WEATHER_NOTIF_ID = 1;
    Realm realm = null;
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
        refresh();
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendNotification() {
        SimpleDateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        realm = Realm.getDefaultInstance();
        WeatherData weatherData = realm.where(WeatherData.class).findFirst();
        Log.d("Service", "Данные из бд " + String.valueOf(weatherData.getTemp() + " " + weatherData.getTemp_min() + " " + weatherData.getTemp_max() + " " + weatherData.getWindSpeed() + ""
                + weatherData.getPressure() + " " + weatherData.getCity() + " " + weatherData.getWeatherMain() + " " + weatherData.getWeatherDescription() + " " + weatherData.getDateAndTime()));
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(weatherData.getDateAndTime())
                .setContentText(weatherData.getWeatherMain() + "\n" + weatherData.getWeatherDescription());
        Notification notification = builder.build();
        manager.notify(CURRENT_WEATHER_NOTIF_ID, notification);

    }
    private void refresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    WeatherTask weatherTask = new WeatherTask();
                    weatherTask.execute(MainActivity.class);
                    sendNotification();
                    Log.d("Service", "Обратились к сервису и вывели погоду");
                    Thread.sleep(60000);
                    refresh();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
