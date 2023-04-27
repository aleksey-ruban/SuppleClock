package ru.alekseyruban.suppleclock.data.data_sources.room.entites;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.util.TableInfo;

import java.util.ArrayList;

@Entity(tableName = "alarm_common_entity")
public class AlarmCommonEntity {

    @PrimaryKey(autoGenerate = true)
    public int commonId;

    @ColumnInfo
    public int alarmType;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "activated")
    public boolean activated;

    @ColumnInfo(name = "allowed_delays")
    public int allowedDelays;

    @ColumnInfo(name = "necessarily_wake_up")
    public boolean necessarilyWakeup;

    @ColumnInfo(name = "morning_time")
    public boolean morningTime;

    @ColumnInfo(name = "default_music")
    public boolean defaultMusic;

    @ColumnInfo(name = "music")
    public ArrayList<String> music;

    @Ignore
    public AlarmCommonEntity() { }

    public AlarmCommonEntity(int alarmType, String name, int allowedDelays, boolean necessarilyWakeup, boolean morningTime, boolean defaultMusic, ArrayList<String> music) {
        this.alarmType = alarmType;
        this.name = name;
        this.activated = true;
        this.allowedDelays = allowedDelays;
        this.necessarilyWakeup = necessarilyWakeup;
        this.morningTime = morningTime;
        this.defaultMusic = defaultMusic;
        this.music = music;
    }

}
