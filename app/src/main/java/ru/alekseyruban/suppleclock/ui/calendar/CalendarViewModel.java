package ru.alekseyruban.suppleclock.ui.calendar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.alekseyruban.suppleclock.data.models.AlarmClockItem;
import ru.alekseyruban.suppleclock.data.repositories.AlarmItemsRepository;
import ru.alekseyruban.suppleclock.data.repositories.CalendarDayAlarmsRepository;

public class CalendarViewModel extends ViewModel {
    private CalendarDayAlarmsRepository repo;
    private LiveData<List<AlarmClockItem>> mItems;

    public CalendarViewModel() {

        this.repo = new CalendarDayAlarmsRepository();
        this.mItems = repo.getData();
    }
    public LiveData<List<AlarmClockItem>> getItems() {
        return mItems;
    }
}