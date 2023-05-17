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
import androidx.core.app.NotificationManagerCompat;

import ru.alekseyruban.suppleclock.MainActivity;
import ru.alekseyruban.suppleclock.R;

public class NotificationService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("ALARM_CLOCK", "START SERVICE!!!");

        CharSequence name = getString(R.string.notification_before_sleep_channel_name);
        String description = getString(R.string.notifiaction_defore_sleep_channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(getString(R.string.notification_before_sleep_channel_id), name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        Intent intentM = new Intent(this, MainActivity.class);
        intentM.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intentM, PendingIntent.FLAG_IMMUTABLE);

        Notification notification =
                new Notification.Builder(this, getString(R.string.notification_channel_id))
                        .setContentTitle(getText(R.string.go_sleep_message))
                        .setContentText(getText(R.string.go_sleep_ticket))
                        .setSmallIcon(R.drawable.ic_notification_icon)
                        .setContentIntent(pendingIntent)
                        .build();

        notificationManager.notify(5678, notification);

        stopSelf();

        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
