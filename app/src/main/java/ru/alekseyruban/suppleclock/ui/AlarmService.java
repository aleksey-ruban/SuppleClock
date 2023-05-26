package ru.alekseyruban.suppleclock.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import ru.alekseyruban.suppleclock.MainActivity;
import ru.alekseyruban.suppleclock.R;

public class AlarmService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("ALARM_CLOCK", "START SERVICE!!!");

        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("active_alarm_common_id", intent.getIntExtra("active_alarm_common_id", -1));
        i.putExtra("delays", intent.getIntExtra("delays", 0));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(i);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.alarm_common_id), intent.getIntExtra("active_alarm_common_id", -1));
        editor.putInt(getString(R.string.alarm_delays_allowed), intent.getIntExtra("delays", 0));
        editor.apply();


        CharSequence name = getString(R.string.notification_channel_name);
        String description = getString(R.string.notifiaction_channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(getString(R.string.notification_channel_id), name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        Intent intentM = new Intent(this, MainActivity.class);
        intentM.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentM, PendingIntent.FLAG_IMMUTABLE);

        Notification notification =
                new Notification.Builder(this, getString(R.string.notification_channel_id))
                        .setContentTitle(getText(R.string.notification_title))
                        .setSmallIcon(R.drawable.ic_notification_icon)
                        .setContentIntent(pendingIntent)
                        .build();

        startForeground(5432, notification);

        Intent intent1 = new Intent(this, MusicService.class);
        this.startService(intent1);

        stopSelf();

        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
