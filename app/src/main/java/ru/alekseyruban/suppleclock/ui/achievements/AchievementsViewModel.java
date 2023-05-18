package ru.alekseyruban.suppleclock.ui.achievements;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.alekseyruban.suppleclock.data.data_sources.AchievementsDataSource;
import ru.alekseyruban.suppleclock.data.models.AchievementItem;
import ru.alekseyruban.suppleclock.data.models.PresentableAlarmClockItem;
import ru.alekseyruban.suppleclock.data.repositories.AlarmItemsRepository;

public class AchievementsViewModel extends ViewModel {

    private AchievementsDataSource repo;
    private LiveData<List<AchievementItem>> databaseItems;

    public AchievementsViewModel() {
        this.repo = new AchievementsDataSource();
        this.databaseItems = repo.items();
    }

    public LiveData<List<AchievementItem>> getDataSourceItems() {
        return databaseItems;
    }
}