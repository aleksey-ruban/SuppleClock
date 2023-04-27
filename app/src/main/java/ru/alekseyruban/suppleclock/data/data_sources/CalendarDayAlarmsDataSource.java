package ru.alekseyruban.suppleclock.data.data_sources;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import ru.alekseyruban.suppleclock.data.models.PresentableAlarmClockItem;

public class CalendarDayAlarmsDataSource {

    public LiveData<List<PresentableAlarmClockItem>> items() {
        MutableLiveData<List<PresentableAlarmClockItem>> result = new MutableLiveData<>();

        ArrayList<PresentableAlarmClockItem> resultArr = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            resultArr.add(new PresentableAlarmClockItem(0, 0,"Работа", 9, 20, "Каждый день", true));
        }

        result.postValue(resultArr);

        return result;
    }

}
