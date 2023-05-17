package ru.alekseyruban.suppleclock.ui.alarms_list;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.alekseyruban.suppleclock.data.models.PresentableAlarmClockItem;
import ru.alekseyruban.suppleclock.data.repositories.AlarmItemsRepository;

public class AlarmsListViewModel extends AndroidViewModel {
    private AlarmItemsRepository repo;
    private LiveData<List<PresentableAlarmClockItem>> fakeItems;
    private LiveData<List<PresentableAlarmClockItem>> databaseItems;


    public AlarmsListViewModel(Application application) {
        super(application);

        this.repo = new AlarmItemsRepository(application);
        this.fakeItems = repo.getData();
        this.databaseItems = repo.getDatabaseData();
    }

    public LiveData<List<PresentableAlarmClockItem>> getFakeItems() {
        return fakeItems;
    }
    public LiveData<List<PresentableAlarmClockItem>> getDatabaseItems() {
        return databaseItems;
    }

    public void switchAlarmActive(int commonId) {
        repo.switchAlarmActive(commonId);
    }
}