package ru.alekseyruban.suppleclock.data.repositories;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.alekseyruban.suppleclock.data.data_sources.MusicItemsDataSource;
import ru.alekseyruban.suppleclock.data.models.MusicItem;

public class MusicItemsRepository {

    private MusicItemsDataSource mDataSource;

    public MusicItemsRepository() {
        this.mDataSource = new MusicItemsDataSource();
    }

    public LiveData<List<MusicItem>> getData() {
        return mDataSource.items();
    }

}
