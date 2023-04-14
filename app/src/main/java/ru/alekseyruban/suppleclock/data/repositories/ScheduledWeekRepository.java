package ru.alekseyruban.suppleclock.data.repositories;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.alekseyruban.suppleclock.data.data_sources.ScheduledWeekDataSource;
import ru.alekseyruban.suppleclock.data.models.ScheduledWeekItem;

public class ScheduledWeekRepository {

    private ScheduledWeekDataSource mDataSource;

    public ScheduledWeekRepository() {
        this.mDataSource = new ScheduledWeekDataSource();
    }

    public LiveData<List<ScheduledWeekItem>> getData() {
        return mDataSource.items();
    }

}
