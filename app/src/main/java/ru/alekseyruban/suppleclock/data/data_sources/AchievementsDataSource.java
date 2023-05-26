package ru.alekseyruban.suppleclock.data.data_sources;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import ru.alekseyruban.suppleclock.data.models.AchievementItem;

public class AchievementsDataSource {

    public LiveData<List<AchievementItem>> items() {
        MutableLiveData<List<AchievementItem>> result = new MutableLiveData<>();

        ArrayList<AchievementItem> resultArr = new ArrayList<>();

        resultArr.add(new AchievementItem("@mipmap/ic_first_try_achievement", "Недельный марафон", "Вы не откладывали сигнал будильника на протяжении недели", false));
        resultArr.add(new AchievementItem("@mipmap/ic_holiday_achievement", "Отличный отпуск", "За весь отпуск вы ни разу не использовали будильник", false));
        resultArr.add(new AchievementItem("@mipmap/ic_planning_achievement", "Планы на будущее", "Вы смотрите свой график и строите планы", false));
        resultArr.add(new AchievementItem("@mipmap/ic_notification_achievement", "Здоровый сон", "Вы настроили напоминания об отходе ко сну", false));

        result.postValue(resultArr);

        return result;
    }


}
