package com.example.kazimir.weatherservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class WeatherReciever extends BroadcastReceiver {
    public final static String BROADCAST_PARAM_STATUS = "Updated";

    private final static int RESULT_UPDATE = 1;

    public WeatherReciever() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Reciever", "Ресивер уведомлен");
        Log.d("Reciever", context.getClass().getCanonicalName());
        Log.d("Reciever", context.getApplicationContext().getClass().getCanonicalName());
    }
}
