package ru.alekseyruban.suppleclock.ui;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.PowerManager;
import android.util.Log;

import ru.alekseyruban.suppleclock.MainActivity;

public class AlarmReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int commonId = intent.getIntExtra("commonId", -1);
        int delays = intent.getIntExtra("delays", 0);

        Log.i("ALARM_CLOCK", "SIGNAL, SIGNAL!!! " + commonId);

//        Intent i = new Intent(context, MainActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        i.putExtra("active_alarm_common_id", commonId);
//
//        context.startActivity(i);


//        Intent i = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        i.putExtra("active_alarm_common_id", commonId);
//        context.startActivity(i);

//        Intent i = new Intent(context, MainActivity.class);
//        i.putExtra("active_alarm_common_id", commonId);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        context.startActivity(i);

        Intent i = new Intent(context, AlarmService.class); // Build the intent for the service
        i.putExtra("active_alarm_common_id", commonId);
        i.putExtra("delays", delays);
        context.getApplicationContext().startForegroundService(i);

    }
}
