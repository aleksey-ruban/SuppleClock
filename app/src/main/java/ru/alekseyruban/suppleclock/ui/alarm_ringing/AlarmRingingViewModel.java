package ru.alekseyruban.suppleclock.ui.alarm_ringing;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import ru.alekseyruban.suppleclock.data.data_sources.room.entites.AlarmCommonEntity;
import ru.alekseyruban.suppleclock.data.data_sources.room.root.AppDatabase;
import ru.alekseyruban.suppleclock.data.repositories.AlarmItemsRepository;

public class AlarmRingingViewModel extends AndroidViewModel {

    private AlarmItemsRepository repo;

    private MutableLiveData<AlarmCommonEntity> alarmCommonEntity = new MutableLiveData<>();

    public LiveData<AlarmCommonEntity> alarmCommonEntity () { return alarmCommonEntity; }


    public AlarmRingingViewModel(@NonNull Application application, int commonId) {
        super(application);

        repo = new AlarmItemsRepository(application);

        AppDatabase.databaseWriteExecutor.execute(() -> {
            alarmCommonEntity.postValue(AppDatabase.getDatabase(getApplication().getApplicationContext()).alarmCommonDAO().getAlarmCommonById(commonId));
        });

    }

    private MutableLiveData<String> data = new MutableLiveData<>();

    public void setData(String newData) {
        data.setValue(newData);
    }

    public LiveData<String> getData() {
        return data;
    }


}