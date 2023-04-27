package ru.alekseyruban.suppleclock.ui.alarm_details;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import ru.alekseyruban.suppleclock.data.models.AlarmCommonItem;

public class AlarmDetailsViewModel extends AndroidViewModel {

    private MutableLiveData<String> name = new MutableLiveData<>();

    private int allowedDelays;

    private final MutableLiveData<Integer> allowedDelaysContainer = new MutableLiveData<>();
    private final MutableLiveData<Boolean> necessarilyWakeup = new MutableLiveData<>();
    private final MutableLiveData<Boolean> morningTime = new MutableLiveData<>();

    private final MutableLiveData<Boolean> defaultMusic = new MutableLiveData<>();

    private final MutableLiveData<ArrayList<String>> music = new MutableLiveData<>();

    public LiveData<String> name() {return name;}

    public LiveData<Integer> allowedDelaysContainer() {
        return allowedDelaysContainer;
    }

    public LiveData<Boolean> necessarilyWakeup() {
        return necessarilyWakeup;
    }

    public LiveData<Boolean> morningTime() {
        return morningTime;
    }

    public LiveData<Boolean> defaultMusic() {
        return defaultMusic;
    }

    public LiveData<ArrayList<String>> music() {
        return music;
    }

    public AlarmDetailsViewModel(Application application) {
        super(application);
        allowedDelays = 5;
        name.setValue("Будильник");
        allowedDelaysContainer.setValue(allowedDelays);
        defaultMusic.setValue(true);
        music.setValue(new ArrayList<>());
        necessarilyWakeup.setValue(false);
        morningTime.setValue(false);
    }

    public String getAllowedDelays() {
        return allowedDelays + " раз";
    }

    public void changeName(String name) {
        if (name.equals("")) {
            name = "Будильник";
        }
        this.name.setValue(name);
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

    public AlarmCommonItem formAlarmCommon() {
        return new AlarmCommonItem(name.getValue(), allowedDelays,
                Boolean.TRUE.equals(necessarilyWakeup.getValue()), Boolean.TRUE.equals(morningTime.getValue()),
                Boolean.TRUE.equals(defaultMusic.getValue()), music.getValue());
    }

    public void setCommonInfo(AlarmCommonItem item) {
        name.setValue(item.getName());
        allowedDelays = item.getAllowedDelays();
        allowedDelaysContainer.setValue(allowedDelays);
        necessarilyWakeup.setValue(item.isNecessarilyWakeup());
        morningTime.setValue(item.isMorningTime());
        defaultMusic.setValue(item.isDefaultMusic());
        music.setValue(item.getMusic());
    }
}