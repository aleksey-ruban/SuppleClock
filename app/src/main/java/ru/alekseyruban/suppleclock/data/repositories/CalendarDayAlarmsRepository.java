package ru.alekseyruban.suppleclock.data.repositories;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.alekseyruban.suppleclock.data.data_sources.CalendarDayAlarmsDataSource;
import ru.alekseyruban.suppleclock.data.models.PresentableAlarmClockItem;

public class CalendarDayAlarmsRepository {
    private CalendarDayAlarmsDataSource mDataSource;

    public CalendarDayAlarmsRepository() {
        this.mDataSource = new CalendarDayAlarmsDataSource();
    }

    public LiveData<List<PresentableAlarmClockItem>> getData() {
        return mDataSource.items();
    }

}