package ru.alekseyruban.suppleclock.data.data_sources;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import ru.alekseyruban.suppleclock.data.models.ScheduledWeekItem;

public class ScheduledWeekDataSource {

    public LiveData<List<ScheduledWeekItem>> items() {
        MutableLiveData<List<ScheduledWeekItem>> result = new MutableLiveData<>();

        ArrayList<ScheduledWeekItem> resultArr = new ArrayList<>();

        for (int i = 1; i < 3; i++) {
            ArrayList<Integer> wh = new ArrayList<>();
            wh.add(9);
            wh.add(8);
            wh.add(10);
            wh.add(11);
            wh.add(8);
            wh.add(0);
            wh.add(0);

            ArrayList<Integer> wm = new ArrayList<>();
            wm.add(20);
            wm.add(40);
            wm.add(0);
            wm.add(0);
            wm.add(0);
            wm.add(0);
            wm.add(0);

            ArrayList<Integer> h = new ArrayList<>();
            h.add(5);
            h.add(6);
            resultArr.add(new ScheduledWeekItem(wh, wm, h));
        }

        result.postValue(resultArr);

        return result;
    }
}
