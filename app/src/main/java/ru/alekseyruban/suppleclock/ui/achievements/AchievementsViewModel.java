package ru.alekseyruban.suppleclock.ui.achievements;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AchievementsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AchievementsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}