package ru.alekseyruban.suppleclock.ui.alarms_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AlarmsListViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AlarmsListViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}