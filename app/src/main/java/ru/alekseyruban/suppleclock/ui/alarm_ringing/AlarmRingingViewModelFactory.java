package ru.alekseyruban.suppleclock.ui.alarm_ringing;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class AlarmRingingViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private int mParam;


    public AlarmRingingViewModelFactory(Application application, int commonId) {
        this.application = application;
        mParam = commonId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AlarmRingingViewModel(application, mParam);
    }

}