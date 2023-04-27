package ru.alekseyruban.suppleclock.ui.MusicSelection;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.alekseyruban.suppleclock.data.models.MusicItem;
import ru.alekseyruban.suppleclock.data.repositories.MusicItemsRepository;

public class SelectingMusicViewModel extends ViewModel {
    private MusicItemsRepository repo;
    private LiveData<List<MusicItem>> mItems;

    public SelectingMusicViewModel() {

        this.repo = new MusicItemsRepository();
        this.mItems = repo.getData();
    }
    public LiveData<List<MusicItem>> getItems() {
        return mItems;
    }
}