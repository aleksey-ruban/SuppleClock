package ru.alekseyruban.suppleclock.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationSendReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, NotificationService.class);
        context.getApplicationContext().startService(i);
    }
}
