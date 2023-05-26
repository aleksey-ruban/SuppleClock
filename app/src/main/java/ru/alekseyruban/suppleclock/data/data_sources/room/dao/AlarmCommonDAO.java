package ru.alekseyruban.suppleclock.data.data_sources.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import ru.alekseyruban.suppleclock.data.data_sources.room.CommonAndSimple;
import ru.alekseyruban.suppleclock.data.data_sources.room.entites.AlarmCommonEntity;

@Dao
public interface AlarmCommonDAO {

    @Query("SELECT * FROM alarm_common_entity")
    List<AlarmCommonEntity> getAllCommonEntities();

    @Insert
    long addNewAlarmCommon(AlarmCommonEntity newItem);

    @Transaction
    @Query("SELECT * FROM alarm_common_entity")
    LiveData<List<CommonAndSimple>> getCommonAndSimple();

    @Transaction
    @Query("SELECT * FROM alarm_common_entity WHERE commonId = :id LIMIT 1")
    LiveData<CommonAndSimple> getCommonAndSimpleById(int id);

    @Update
    void update(AlarmCommonEntity item);

    @Query("SELECT * FROM alarm_common_entity WHERE commonId = :id")
    LiveData<AlarmCommonEntity> getAlarmCommonWithId(int id);

    @Query("SELECT * FROM alarm_common_entity WHERE commonId = :id")
    AlarmCommonEntity getAlarmCommonById(int id);

    @Query("DELETE FROM alarm_common_entity WHERE commonId = :id")
    void deleteAlarmCommonWithCommonId(int id);

}


