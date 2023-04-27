package ru.alekseyruban.suppleclock.ui.schedule_clock;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.alekseyruban.suppleclock.data.models.ScheduledWeekItem;
import ru.alekseyruban.suppleclock.data.repositories.ScheduledWeekRepository;

public class ScheduleClockViewModel extends ViewModel {
    private ScheduledWeekRepository repo;
    private LiveData<List<ScheduledWeekItem>> mItems;

    public ScheduleClockViewModel() {

        this.repo = new ScheduledWeekRepository();
        this.mItems = repo.getData();
    }

    public LiveData<List<ScheduledWeekItem>> getItems() {
        return mItems;
    }
}