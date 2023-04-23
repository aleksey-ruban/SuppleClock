package ru.alekseyruban.suppleclock.ui.simpleClock;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.Instant;
import java.util.ArrayList;


public class SimpleClockViewModel extends ViewModel {

    private ArrayList<Boolean> selectedDays;


    private MutableLiveData<ArrayList<Boolean>> selectedDaysContainer = new MutableLiveData<>();
    private MutableLiveData<Integer> hours = new MutableLiveData<>();
    private MutableLiveData<Integer> minutes = new MutableLiveData<>();

    public LiveData<ArrayList<Boolean>> selectedDaysContainer() {
        return selectedDaysContainer;
    }
    public LiveData<Integer> hours() {return hours;}
    public LiveData<Integer> minutes() {return minutes;}

    public SimpleClockViewModel() {
        selectedDays = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            selectedDays.add(false);
        }
    }

    public void selectDay(int position) {
        selectedDays.set(position, !selectedDays.get(position));
        selectedDaysContainer.setValue(selectedDays);
    }

    public void changeTime(int hours, int minutes) {
        this.hours.setValue(hours);
        this.minutes.setValue(minutes);
    }

}