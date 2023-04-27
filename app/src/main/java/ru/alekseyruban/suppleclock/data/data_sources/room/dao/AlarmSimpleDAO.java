package ru.alekseyruban.suppleclock.data.data_sources.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.alekseyruban.suppleclock.data.data_sources.room.entites.AlarmSimpleEntity;

@Dao
public interface AlarmSimpleDAO {

    @Query("SELECT * FROM alarm_simple_entity")
    LiveData<List<AlarmSimpleEntity>> getAllAlarms();

    @Insert
    void addNewAlarmSimple(AlarmSimpleEntity newItem);

    @Update
    void update(AlarmSimpleEntity alarmSimpleEntity);

    @Query("SELECT * FROM alarm_simple_entity WHERE alarmCommonId = :id")
    AlarmSimpleEntity getAlarmSimpleWithCommonId(int id);

    @Query("DELETE FROM alarm_simple_entity WHERE alarmCommonId = :id")
    void deleteAlarmSimpleWithCommonId(int id);

}
