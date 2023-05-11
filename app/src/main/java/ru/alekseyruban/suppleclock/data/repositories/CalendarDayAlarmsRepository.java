package ru.alekseyruban.suppleclock.data.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import ru.alekseyruban.suppleclock.data.data_sources.CalendarDayAlarmsDataSource;
import ru.alekseyruban.suppleclock.data.data_sources.room.CommonAndSimple;
import ru.alekseyruban.suppleclock.data.data_sources.room.entites.AlarmCommonEntity;
import ru.alekseyruban.suppleclock.data.data_sources.room.entites.AlarmSimpleEntity;
import ru.alekseyruban.suppleclock.data.data_sources.room.root.AppDatabase;
import ru.alekseyruban.suppleclock.data.models.AlarmSimpleItem;
import ru.alekseyruban.suppleclock.data.models.PresentableAlarmClockItem;

public class CalendarDayAlarmsRepository {
    private CalendarDayAlarmsDataSource mDataSource;
    private AppDatabase databaseSource;

    public CalendarDayAlarmsRepository(Application application) {
        this.mDataSource = new CalendarDayAlarmsDataSource();
        this.databaseSource = AppDatabase.getDatabase(application);
    }

    public LiveData<List<PresentableAlarmClockItem>> getData() {
        return mDataSource.items();
    }

    public LiveData<List<CommonAndSimple>> getDatabaseData() {

        return databaseSource.alarmCommonDAO().getCommonAndSimple();


    }

    public void updateAlarmSimple(AlarmSimpleItem alarmSimpleItem, int id) {
        AppDatabase.databaseWriteExecutor.execute(() -> {

            AlarmCommonEntity alarmCommonEntity = new AlarmCommonEntity(0, alarmSimpleItem.getName(), alarmSimpleItem.getAllowedDelays(),
                    alarmSimpleItem.isNecessarilyWakeup(), alarmSimpleItem.isMorningTime(),
                    alarmSimpleItem.isDefaultMusic(), alarmSimpleItem.getMusic());

            alarmCommonEntity.commonId = id;
            int simpleId = databaseSource.alarmSimpleDAO().getAlarmSimpleWithCommonId(id).simpleId;

            AlarmSimpleEntity alarmSimpleEntity = new AlarmSimpleEntity(alarmSimpleItem.getHours(), alarmSimpleItem.getMinutes(), alarmSimpleItem.getAlarmDays(),
                    alarmSimpleItem.getColorNumber(), id);
            alarmSimpleEntity.simpleId = simpleId;

            databaseSource.alarmCommonDAO().update(alarmCommonEntity);
            databaseSource.alarmSimpleDAO().update(alarmSimpleEntity);
        });
    }

}