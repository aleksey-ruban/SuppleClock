package ru.alekseyruban.suppleclock.ui.calendar;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.nio.channels.CancelledKeyException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import ru.alekseyruban.suppleclock.data.data_sources.room.CommonAndSimple;
import ru.alekseyruban.suppleclock.data.data_sources.room.root.AppDatabase;
import ru.alekseyruban.suppleclock.data.models.AlarmCommonItem;
import ru.alekseyruban.suppleclock.data.models.AlarmSimpleItem;
import ru.alekseyruban.suppleclock.data.models.PresentableAlarmClockItem;
import ru.alekseyruban.suppleclock.data.repositories.AlarmItemsRepository;
import ru.alekseyruban.suppleclock.data.repositories.CalendarDayAlarmsRepository;

public class CalendarViewModel extends AndroidViewModel {
    private CalendarDayAlarmsRepository repo;
    private AlarmItemsRepository repoAlarm;
    private LiveData<List<PresentableAlarmClockItem>> mItems;

    private MutableLiveData<String> selectedMonthContainer = new MutableLiveData<>();

    public LiveData<String> selectedMonthContainer() {
        return selectedMonthContainer;
    }

    private MutableLiveData<PresentableAlarmClockItem> alarmItemToChange = new MutableLiveData<>();

    public LiveData<PresentableAlarmClockItem> alarmItemToChange() {
        return alarmItemToChange;
    }

    private LiveData<List<CommonAndSimple>> databaseItems;

    public Date selectedDate;

    public CalendarViewModel(Application application) {
        super(application);

        this.repo = new CalendarDayAlarmsRepository(application);
        this.repoAlarm = new AlarmItemsRepository(application);
        this.mItems = repo.getData();
        this.databaseItems = repo.getDatabaseData();
    }
    public LiveData<List<PresentableAlarmClockItem>> getItems() {
        return mItems;
    }

    private boolean wasOpenedToEdit = false;

    public void setWasOpenedToEdit(boolean wasOpenedToEdit) {
        this.wasOpenedToEdit = wasOpenedToEdit;
    }

    public boolean getWasOpenedToEdit() {
        return wasOpenedToEdit;
    }

    private boolean wasSwitchedActive = false;

    public void setWasSwitchedActive(boolean wasOpenedToEdit) {
        this.wasSwitchedActive = wasOpenedToEdit;
    }

    public boolean getWasSwitchedActive() {
        return wasSwitchedActive;
    }

    public void setAlarmIdToChange(PresentableAlarmClockItem item) {
        alarmItemToChange.setValue(item);
    }

    public void scrollSelectedMonth(Date selectedDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(selectedDate);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat month_date = new SimpleDateFormat("LLLL");
        String month_name = month_date.format(cal.getTime());
        month_name = month_name.substring(0, 1).toUpperCase() + month_name.substring(1);
        int year = cal.get(Calendar.YEAR);
        selectedMonthContainer.setValue(month_name + " " + year);
        Log.d("SelectedMonth_CalendarViewModel", selectedDate.toString());
    }

    public LiveData<List<PresentableAlarmClockItem>> getDatabaseItems(Date date) {

        LiveData<List<CommonAndSimple>> filtered = Transformations.map(
                databaseItems,
                (values) -> values.stream().filter(commonAndSimple -> {
                    int count = 0;
                    for (Boolean i : commonAndSimple.alarmSimple.alarmDays) {
                        if (i.equals(true)) {
                            count++;
                        }
                    }
                    if (count == 0) {

                        Calendar c = Calendar.getInstance();
                        c.setTime(new Date());
                        int dayOfWeek = c.get(Calendar.DAY_OF_MONTH);
                        int mount = c.get(Calendar.MONTH);
                        int year = c.get(Calendar.YEAR);
                        c.setTime(date);
                        int dayOfWeek1 = c.get(Calendar.DAY_OF_MONTH);
                        int mount1 = c.get(Calendar.MONTH);
                        int year1 = c.get(Calendar.YEAR);
                        return (dayOfWeek == dayOfWeek1 && mount == mount1 && year == year1);
                    } else if (count == 7) {
                        return true;
                    } else {
                        Calendar c = Calendar.getInstance();
                        c.setTime(date);
                        int dayOfWeek = (c.get(Calendar.DAY_OF_WEEK) + 5) % 7;
                        return commonAndSimple.alarmSimple.alarmDays.get(dayOfWeek);
                    }
                }).collect(Collectors.toList())
        );

        return Transformations.map(
                filtered,
                (values) -> values.stream().map(CommonAndSimple::toDomainModel).collect(Collectors.toList())
        );
    }

    public void switchAlarmActive(int commonId) {
        repoAlarm.switchAlarmActive(commonId);
    }
}