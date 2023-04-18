package ru.alekseyruban.suppleclock.data.data_sources;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import ru.alekseyruban.suppleclock.data.models.MusicItem;

public class MusicItemsDataSource {

    public LiveData<List<MusicItem>> items() {
        MutableLiveData<List<MusicItem>> result = new MutableLiveData<>();

        ArrayList<MusicItem> resultArr = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            resultArr.add(new MusicItem("Master of Puppets – Metallica"));
        }

        result.postValue(resultArr);

        return result;
    }

}
