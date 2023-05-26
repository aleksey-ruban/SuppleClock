package ru.alekseyruban.suppleclock.ui.simpleClock;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import ru.alekseyruban.suppleclock.data.models.AlarmCommonItem;
import ru.alekseyruban.suppleclock.data.models.AlarmSimpleItem;
import ru.alekseyruban.suppleclock.data.repositories.AlarmItemsRepository;

public class SimpleClockViewModel extends AndroidViewModel {

    private final AlarmItemsRepository repo;

    private int editingAlarmId;

    public void setEditingAlarmId(int editingAlarmId) {
        this.editingAlarmId = editingAlarmId;
    }

    private ArrayList<Boolean> selectedDays;

    private final MutableLiveData<ArrayList<Boolean>> selectedDaysContainer = new MutableLiveData<>();
    private final MutableLiveData<Integer> hours = new MutableLiveData<>();
    private final MutableLiveData<Integer> minutes = new MutableLiveData<>();

    private final MutableLiveData<Integer> colorNumber = new MutableLiveData<>();

    public LiveData<ArrayList<Boolean>> selectedDaysContainer() {
        return selectedDaysContainer;
    }

    private LiveData<AlarmSimpleItem> editingAlarmSimple;

    public SimpleClockViewModel(Application application) {
        super(application);
        this.repo = new AlarmItemsRepository(application);
        selectedDays = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            selectedDays.add(false);
        }
        selectedDaysContainer.setValue(selectedDays);
        colorNumber.setValue(ThreadLocalRandom.current().nextInt(0, 5 + 1));
    }

    public void selectDay(int position) {
        selectedDays.set(position, !selectedDays.get(position));
        selectedDaysContainer.setValue(selectedDays);
    }

    public void changeTime(int hours, int minutes) {
        this.hours.setValue(hours);
        this.minutes.setValue(minutes);
    }

    public void saveAlarm(AlarmCommonItem alarmCommonItem) {

        AlarmSimpleItem alarmSimpleItem = new AlarmSimpleItem(alarmCommonItem.getName(), alarmCommonItem.getAllowedDelays(),
                alarmCommonItem.isNecessarilyWakeup(), alarmCommonItem.isMorningTime(), alarmCommonItem.isDefaultMusic(),
                alarmCommonItem.getMusic(), Objects.requireNonNull(hours.getValue()), Objects.requireNonNull(minutes.getValue()), selectedDays, Objects.requireNonNull(colorNumber.getValue()));
        repo.addAlarmSimple(alarmSimpleItem);
    }

    public void updateAlarm(AlarmCommonItem alarmCommonItem) {
        AlarmSimpleItem alarmSimpleItem = new AlarmSimpleItem(alarmCommonItem.getName(), alarmCommonItem.getAllowedDelays(),
                alarmCommonItem.isNecessarilyWakeup(), alarmCommonItem.isMorningTime(), alarmCommonItem.isDefaultMusic(),
                alarmCommonItem.getMusic(), Objects.requireNonNull(hours.getValue()), Objects.requireNonNull(minutes.getValue()), selectedDays, Objects.requireNonNull(colorNumber.getValue()));
        repo.updateAlarmSimple(alarmSimpleItem, editingAlarmId);
    }

    public void getEditingAlarm(int id) {
        this.editingAlarmSimple = repo.getSimpleItemWithId(id);

    }

    public LiveData<AlarmSimpleItem> getEditingAlarmSimple() {
        return editingAlarmSimple;
    }

    public void updateInfoToEditing() {
        AlarmSimpleItem item = editingAlarmSimple.getValue();
        selectedDays = Objects.requireNonNull(item).getAlarmDays();
        selectedDaysContainer.setValue(selectedDays);
        hours.setValue(item.getHours());
        minutes.setValue(item.getMinutes());
        colorNumber.setValue(item.getColorNumber());
    }

    public void deleteAlarmSimple(int commonId) {
        repo.deleteAlarmSimple(commonId);
    }

}