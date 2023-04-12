package ru.alekseyruban.suppleclock.data.models;

public class AlarmClockItem {

    private String alarm_name;
    private int hours;
    private int minutes;

    private String repeating_mode;
    private boolean activated;

    public String getAlarmName() {
        return alarm_name;
    }

    public String getAlarmTime() {
        return hours + ":" + minutes;
    }

    public String getRepeating_mode() {
        return repeating_mode;
    }

    public boolean getActivated() {
        return activated;
    }

    public AlarmClockItem(String alarm_name, int hours, int minutes, String repeating_mode, boolean activated) {
        this.alarm_name = alarm_name;
        this.hours = hours;
        this.minutes = minutes;
        this.repeating_mode = repeating_mode;
        this.activated = activated;
    }

}
