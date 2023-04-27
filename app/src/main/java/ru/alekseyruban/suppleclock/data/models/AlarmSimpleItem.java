package ru.alekseyruban.suppleclock.data.models;

import java.util.ArrayList;

public class AlarmSimpleItem extends AlarmCommonItem {

//    private int simpleId;
//    public int getSimpleId () { return simpleId;}

    private int hours;
    public int getHours() {return hours;}

    private int minutes;
    public int getMinutes() {return minutes;}

    private ArrayList<Boolean> alarmDays;
    public ArrayList<Boolean> getAlarmDays() {return alarmDays;}

    private int colorNumber;
    public int getColorNumber() {return colorNumber;}

//    private int alarmCommonId;
//    public int getAlarmCommonId() {return alarmCommonId;}

    private AlarmSimpleItem() { }

    public AlarmSimpleItem(String name, int allowedDelays, boolean necessarilyWakeup,
                           boolean morningTime, boolean defaultMusic, ArrayList<String> music,
                           int hours, int minutes, ArrayList<Boolean> alarmDays,
                           int colorNumber) {
        super(name, allowedDelays, necessarilyWakeup, morningTime, defaultMusic, music);

        this.hours = hours;
        this.minutes = minutes;
        this.alarmDays = alarmDays;
        this.colorNumber = colorNumber;
//        this.alarmCommonId = alarmCommonId;
    }

}
