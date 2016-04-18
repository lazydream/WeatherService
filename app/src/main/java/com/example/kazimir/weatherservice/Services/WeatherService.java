package com.example.kazimir.weatherservice.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserCompat;
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
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;

public class WeatherService extends Service {
    private static final int CURRENT_WEATHER_NOTIF_ID = 1;
    private static final int REQUEST_ID = 2;
    public static final String WEATHER_FROM_SERVICE = "Weather from service";

    Realm realm = null;
    private RealmConfiguration realmConfiguration;
    private PendingIntent pendingIntent;

    public WeatherService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        refresh();
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendNotification() {

        realm = Realm.getDefaultInstance();
        WeatherData weatherData = realm.where(WeatherData.class).findFirst();
        NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);


        Notification.Builder builder = new Notification.Builder(this)
                .setContentIntent(pIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(weatherData.getDateAndTime())
                .setContentText(weatherData.getWeatherMain() + "\n" + weatherData.getWeatherDescription());
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(CURRENT_WEATHER_NOTIF_ID, notification);
        realm.close();
    }

    private void refresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    WeatherTask weatherTask = new WeatherTask();
                    weatherTask.execute(MainActivity.class);
                    while(weatherTask.getStatus() != AsyncTask.Status.FINISHED) {
                        Thread.sleep(100);
                    }
                    Intent sendIntent = new Intent("Action");
                    sendBroadcast(sendIntent);

                    sendNotification();
                    Thread.sleep(6000);
                    refresh();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
