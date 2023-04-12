package ru.alekseyruban.suppleclock.data.repositories;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.alekseyruban.suppleclock.data.data_sources.AlarmItemsDataSource;
import ru.alekseyruban.suppleclock.data.models.AlarmClockItem;

public class AlarmItemsRepository {
    private AlarmItemsDataSource mDataSource;

    public AlarmItemsRepository() {
        this.mDataSource = new AlarmItemsDataSource();
    }

    public LiveData<List<AlarmClockItem>> getData() {
        return mDataSource.items();
    }

}
