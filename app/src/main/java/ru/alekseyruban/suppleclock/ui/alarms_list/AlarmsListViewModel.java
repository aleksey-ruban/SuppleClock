package ru.alekseyruban.suppleclock.ui.alarms_list;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.alekseyruban.suppleclock.data.models.AlarmClockItem;
import ru.alekseyruban.suppleclock.data.repositories.AlarmItemsRepository;

public class AlarmsListViewModel extends ViewModel {
    private AlarmItemsRepository repo;
    private LiveData<List<AlarmClockItem>> mItems;

//    private final MutableLiveData<String> mText;

    public AlarmsListViewModel() {

//        mText = new MutableLiveData<>();
//        mText.setValue("This is home fragment");

        this.repo = new AlarmItemsRepository();
        this.mItems = repo.getData();
    }

//    public LiveData<String> getText() {
//        return mText;
//    }
    public LiveData<List<AlarmClockItem>> getItems() {
        return mItems;
    }
}