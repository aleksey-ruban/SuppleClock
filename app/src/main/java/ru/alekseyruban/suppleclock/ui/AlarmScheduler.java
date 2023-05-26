package ru.alekseyruban.suppleclock.ui;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.alekseyruban.suppleclock.R;
import ru.alekseyruban.suppleclock.data.data_sources.room.entites.AlarmCommonEntity;
import ru.alekseyruban.suppleclock.data.data_sources.room.entites.AlarmSimpleEntity;
import ru.alekseyruban.suppleclock.data.data_sources.room.root.AppDatabase;
import ru.alekseyruban.suppleclock.data.repositories.AlarmItemsRepository;

public class AlarmScheduler {

    private Application application;

    private AlarmItemsRepository repo;

    private AlarmManager alarmManager;

    public AlarmScheduler(Application application) {
        this.application = application;
        alarmManager = (AlarmManager) application.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        repo = new AlarmItemsRepository(application);
    }

    public void schedule(int commonId) {
        Intent intent = new Intent(application.getApplicationContext(), AlarmReciever.class);
        intent.putExtra("commonId", commonId);

        Intent intentNotification = new Intent(application.getApplicationContext(), NotificationSendReciever.class);

        SharedPreferences sharedPref = application.getApplicationContext().getSharedPreferences(application.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        boolean needNotify = sharedPref.getBoolean(application.getString(R.string.sleep_notification), false);
        int hoursTarget = sharedPref.getInt(application.getString(R.string.sleep_hours_target), 8);
        int minutesTarget = sharedPref.getInt(application.getString(R.string.sleep_minutes_target), 0);
        int restBeforeSleep = sharedPref.getInt(application.getString(R.string.rest_before_sleep), 30);
        int minutesForNotify = hoursTarget * 60 + minutesTarget + restBeforeSleep;

        AppDatabase.databaseWriteExecutor.execute(() -> {

            AppDatabase databaseSource = AppDatabase.getDatabase(application);
            AlarmCommonEntity alarmCommonEntity = databaseSource.alarmCommonDAO().getAlarmCommonById(commonId);

            Calendar calendar = Calendar.getInstance();

            if (alarmCommonEntity.alarmType == 0) {
                AlarmSimpleEntity alarmSimpleEntity = databaseSource.alarmSimpleDAO().getAlarmSimpleWithCommonId(commonId);

                int n = 0;
                for (int i = 0; i < alarmSimpleEntity.alarmDays.size(); i++) {
                    if (alarmSimpleEntity.alarmDays.get(i)) { n++; }
                }

                @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(application.getApplicationContext(), AlarmManager.RTC_WAKEUP, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntentNotify = PendingIntent.getBroadcast(application.getApplicationContext(), AlarmManager.RTC_WAKEUP, intentNotification, PendingIntent.FLAG_UPDATE_CURRENT);

                calendar.set(Calendar.HOUR_OF_DAY, alarmSimpleEntity.hours);
                calendar.set(Calendar.MINUTE, alarmSimpleEntity.minutes);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                if (n == 0) {
                    calendar.setTime(new Date());
                    calendar.set(Calendar.HOUR_OF_DAY, alarmSimpleEntity.hours);
                    calendar.set(Calendar.MINUTE, alarmSimpleEntity.minutes);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    if (new Date().after(calendar.getTime())) {
                        calendar.add(Calendar.HOUR, 24);
                    }
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                    if (needNotify) {
                        calendar.add(Calendar.MINUTE, -minutesForNotify);
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntentNotify);
                    }
                } else if (n == 7) {
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY, pendingIntent);
                    if (needNotify) {
                        calendar.add(Calendar.MINUTE, -minutesForNotify);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                AlarmManager.INTERVAL_DAY, pendingIntentNotify);
                    }
                } else {
                    for (int i = 0; i < alarmSimpleEntity.alarmDays.size(); i++) {
                        if (alarmSimpleEntity.alarmDays.get(i)) {
                            calendar.set(Calendar.DAY_OF_WEEK, (i + 1) % 7 + 1);
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                    AlarmManager.INTERVAL_DAY * 7, pendingIntent);
                            if (needNotify) {
                                calendar.add(Calendar.MINUTE, -minutesForNotify);
                                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                        AlarmManager.INTERVAL_DAY * 7, pendingIntentNotify);
                            }
                        }
                    }
                }
            } else if (alarmCommonEntity.alarmType == 1) {
                return;
            } else if (alarmCommonEntity.alarmType == 2) {
                return;
            }

        });
    }

    public void snooze(int commonId, int delays) {
        Intent intent = new Intent(application.getApplicationContext(), AlarmReciever.class);
        intent.putExtra("commonId", commonId);
        intent.putExtra("delays", delays);

        AppDatabase.databaseWriteExecutor.execute(() -> {

            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(application.getApplicationContext(), AlarmManager.RTC_WAKEUP, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.MINUTE, 10);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        });
    }

    private void cancelAll() {
        Intent intent = new Intent(application.getApplicationContext(), AlarmReciever.class);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(application.getApplicationContext(), AlarmManager.RTC_WAKEUP, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

    public void resetAll() {
        cancelAll();

        AppDatabase.databaseWriteExecutor.execute(() -> {
            AppDatabase databaseSource = AppDatabase.getDatabase(application);

            List<AlarmCommonEntity> alarmCommonEntities = databaseSource.alarmCommonDAO().getAllCommonEntities();
            for (AlarmCommonEntity i : alarmCommonEntities) {
                if (i.activated) {
                    schedule(i.commonId);
                }
            }
        });
    }

}
