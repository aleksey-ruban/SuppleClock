package ru.alekseyruban.suppleclock.data.data_sources;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import ru.alekseyruban.suppleclock.data.models.AlarmClockItem;

public class AlarmItemsDataSource {
    public LiveData<List<AlarmClockItem>> items() {
        MutableLiveData<List<AlarmClockItem>> result = new MutableLiveData<>();

        ArrayList<AlarmClockItem> resultArr = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            resultArr.add(new AlarmClockItem("Работа", 9, 20, "Каждый день", true));
        }

        result.postValue(resultArr);

        return result;
    }
}
