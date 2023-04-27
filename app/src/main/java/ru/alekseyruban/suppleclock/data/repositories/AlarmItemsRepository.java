package ru.alekseyruban.suppleclock.data.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.List;
import java.util.stream.Collectors;

import ru.alekseyruban.suppleclock.data.data_sources.AlarmItemsDataSource;
import ru.alekseyruban.suppleclock.data.data_sources.room.CommonAndSimple;
import ru.alekseyruban.suppleclock.data.data_sources.room.entites.AlarmCommonEntity;
import ru.alekseyruban.suppleclock.data.data_sources.room.entites.AlarmSimpleEntity;
import ru.alekseyruban.suppleclock.data.data_sources.room.root.AppDatabase;
import ru.alekseyruban.suppleclock.data.models.AlarmSimpleItem;
import ru.alekseyruban.suppleclock.data.models.PresentableAlarmClockItem;
import ru.alekseyruban.suppleclock.ui.AlarmScheduler;

public class AlarmItemsRepository {
    private AlarmItemsDataSource mDataSource;
    private AppDatabase databaseSource;

    private Application application;

    public AlarmItemsRepository(Application application) {
        this.application = application;
        this.mDataSource = new AlarmItemsDataSource();
        this.databaseSource = AppDatabase.getDatabase(application);
    }

    public LiveData<List<PresentableAlarmClockItem>> getDatabaseData() {
        return Transformations.map(
                databaseSource.alarmCommonDAO().getCommonAndSimple(),
                (values) -> values.stream().map(CommonAndSimple::toDomainModel).collect(Collectors.toList())
        );
    }

    public LiveData<AlarmSimpleItem> getSimpleItemWithId(int alarmId) {
        return Transformations.map(
                databaseSource.alarmCommonDAO().getCommonAndSimpleById(alarmId),
                CommonAndSimple::toSimpleModel
        );
    }

//    public AlarmSimpleItem getAlarmSimpleWithId(int alarmId) {
////        Log.i("DQFMEO_DNQ", alarmId + "");
////        return Transformations.map(
////                databaseSource.alarmCommonDAO().getAlarmSimpleWithId(alarmId),
////                CommonAndSimple::toSimpleModel);
//
//
//    }

//    public void addItem(Item newItem) {
//        AppDatabase.databaseWriteExecutor.execute(() -> {
//            databaseSource.itemDAO().addNewItem(new ItemEntity(newItem.getValue()));
//        });
//    }

    public LiveData<List<PresentableAlarmClockItem>> getData() {
        return mDataSource.items();
    }

    public void addAlarmSimple(AlarmSimpleItem alarmSimpleItem) {
        AppDatabase.databaseWriteExecutor.execute(() -> {

            AlarmCommonEntity alarmCommonEntity = new AlarmCommonEntity(0, alarmSimpleItem.getName(), alarmSimpleItem.getAllowedDelays(),
                                                                        alarmSimpleItem.isNecessarilyWakeup(), alarmSimpleItem.isMorningTime(),
                                                                        alarmSimpleItem.isDefaultMusic(), alarmSimpleItem.getMusic());

            int id = (int) databaseSource.alarmCommonDAO().addNewAlarmCommon(alarmCommonEntity);

            AlarmSimpleEntity alarmSimpleEntity = new AlarmSimpleEntity(alarmSimpleItem.getHours(), alarmSimpleItem.getMinutes(), alarmSimpleItem.getAlarmDays(),
                    alarmSimpleItem.getColorNumber(), id);

            databaseSource.alarmSimpleDAO().addNewAlarmSimple(alarmSimpleEntity);

            AlarmScheduler alarmScheduler = new AlarmScheduler(application);
            alarmScheduler.schedule(id);
        });
    }

    public void updateAlarmSimple(AlarmSimpleItem alarmSimpleItem, int id) {
        AppDatabase.databaseWriteExecutor.execute(() -> {

            AlarmCommonEntity alarmCommonEntity = new AlarmCommonEntity(0, alarmSimpleItem.getName(), alarmSimpleItem.getAllowedDelays(),
                    alarmSimpleItem.isNecessarilyWakeup(), alarmSimpleItem.isMorningTime(),
                    alarmSimpleItem.isDefaultMusic(), alarmSimpleItem.getMusic());

            alarmCommonEntity.commonId = id;
            int simpleId = databaseSource.alarmSimpleDAO().getAlarmSimpleWithCommonId(id).simpleId;

            AlarmSimpleEntity alarmSimpleEntity = new AlarmSimpleEntity(alarmSimpleItem.getHours(), alarmSimpleItem.getMinutes(), alarmSimpleItem.getAlarmDays(),
                    alarmSimpleItem.getColorNumber(), id);
            alarmSimpleEntity.simpleId = simpleId;

            databaseSource.alarmCommonDAO().update(alarmCommonEntity);
            databaseSource.alarmSimpleDAO().update(alarmSimpleEntity);
        });
    }

    public void deleteAlarmSimple(int commonId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            databaseSource.alarmCommonDAO().deleteAlarmCommonWithCommonId(commonId);
            databaseSource.alarmSimpleDAO().deleteAlarmSimpleWithCommonId(commonId);
        });
    }

}
