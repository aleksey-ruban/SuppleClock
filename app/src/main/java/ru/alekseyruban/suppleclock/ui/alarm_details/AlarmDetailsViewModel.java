package ru.alekseyruban.suppleclock.ui.alarm_details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AlarmDetailsViewModel extends ViewModel {

    private int allowedDelays;

    private MutableLiveData<Integer> allowedDelaysContainer = new MutableLiveData<>();
    private MutableLiveData<Boolean> necessarilyWakeup = new MutableLiveData<>();
    private MutableLiveData<Boolean> morningTime = new MutableLiveData<>();

    public LiveData<Integer> allowedDelaysContainer() {
        return allowedDelaysContainer;
    }

    public LiveData<Boolean> necessarilyWakeup() {
        return necessarilyWakeup;
    }

    public LiveData<Boolean> morningTime() {
        return morningTime;
    }

    public AlarmDetailsViewModel() {
        allowedDelays = 5;
        necessarilyWakeup.setValue(false);
        morningTime.setValue(false);
    }

    public String getAllowedDelays() {
        return allowedDelays + " раз";
    }

    public void changeAllowedDelays(boolean increasing) {
        if (increasing) {
            allowedDelays++;
        } else {
            if (allowedDelays > 0) {
                allowedDelays--;
            }
        }
        allowedDelaysContainer.setValue(allowedDelays);
    }

    public void switchNecessarilyWakeup() {
        necessarilyWakeup.setValue(Boolean.FALSE.equals(necessarilyWakeup.getValue()));
    }

    public void switchMorningTime() {
        morningTime.setValue(Boolean.FALSE.equals(morningTime.getValue()));
    }
}