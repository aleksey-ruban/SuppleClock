package ru.alekseyruban.suppleclock.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int commonId = intent.getIntExtra("commonId", -1);
        int delays = intent.getIntExtra("delays", 0);

        Log.i("ALARM_CLOCK", "SIGNAL, SIGNAL!!! " + commonId);

        Intent i = new Intent(context, AlarmService.class); // Build the intent for the service
        i.putExtra("active_alarm_common_id", commonId);
        i.putExtra("delays", delays);
        context.getApplicationContext().startForegroundService(i);

    }
}
