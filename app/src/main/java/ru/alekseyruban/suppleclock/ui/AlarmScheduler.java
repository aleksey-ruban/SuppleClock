package ru.alekseyruban.suppleclock.ui;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

import ru.alekseyruban.suppleclock.data.data_sources.room.entites.AlarmCommonEntity;
import ru.alekseyruban.suppleclock.data.data_sources.room.entites.AlarmSimpleEntity;
import ru.alekseyruban.suppleclock.data.data_sources.room.root.AppDatabase;
import ru.alekseyruban.suppleclock.data.models.AlarmSimpleItem;
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

                calendar.set(Calendar.HOUR_OF_DAY, alarmSimpleEntity.hours);
                calendar.set(Calendar.MINUTE, alarmSimpleEntity.minutes);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                if (n == 0) {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                } else if (n == 7) {
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY, pendingIntent);
                } else {
                    for (int i = 0; i < alarmSimpleEntity.alarmDays.size(); i++) {
                        if (alarmSimpleEntity.alarmDays.get(i)) {
                            calendar.set(Calendar.DAY_OF_WEEK, (i + 1) % 7 + 1);
                            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                    AlarmManager.INTERVAL_DAY * 7, pendingIntent);
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

}
