package ru.alekseyruban.suppleclock.data.data_sources.room.entites;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "alarm_simple_entity")
public class AlarmSimpleEntity {

    @PrimaryKey(autoGenerate = true)
    public int simpleId;

    @ColumnInfo
    public int hours;

    @ColumnInfo
    public int minutes;

    @ColumnInfo
    public ArrayList<Boolean> alarmDays;

    @ColumnInfo
    public int colorNumber;

    @ColumnInfo
    public int alarmCommonId;

    @Ignore
    public AlarmSimpleEntity() { }

    public AlarmSimpleEntity(int hours, int minutes, ArrayList<Boolean> alarmDays, int colorNumber, int alarmCommonId) {
        this.hours = hours;
        this.minutes = minutes;
        this.alarmDays = alarmDays;
        this.colorNumber = colorNumber;
        this.alarmCommonId = alarmCommonId;
    }

}
