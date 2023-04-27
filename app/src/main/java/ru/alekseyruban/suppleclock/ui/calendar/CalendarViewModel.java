package ru.alekseyruban.suppleclock.ui.calendar;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.alekseyruban.suppleclock.data.models.PresentableAlarmClockItem;
import ru.alekseyruban.suppleclock.data.repositories.CalendarDayAlarmsRepository;

public class CalendarViewModel extends ViewModel {
    private CalendarDayAlarmsRepository repo;
    private LiveData<List<PresentableAlarmClockItem>> mItems;

    private MutableLiveData<String> selectedMonthContainer = new MutableLiveData<>();

    public LiveData<String> selectedMonthContainer() {
        return selectedMonthContainer;
    }

    public CalendarViewModel() {

        this.repo = new CalendarDayAlarmsRepository();
        this.mItems = repo.getData();
    }
    public LiveData<List<PresentableAlarmClockItem>> getItems() {
        return mItems;
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
}