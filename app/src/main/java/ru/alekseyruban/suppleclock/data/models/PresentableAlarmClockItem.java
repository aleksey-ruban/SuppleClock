package ru.alekseyruban.suppleclock.data.models;

public class PresentableAlarmClockItem {

    private int alarmId;

    private int alarmType;

    private String alarm_name;
    private int hours;
    private int minutes;

    private String repeating_mode;
    private boolean activated;

    public String getAlarmName() {
        return alarm_name;
    }

    public String getAlarmTime() {
        return hours + ":" + (minutes < 10 ? "0" + minutes : minutes);
    }

    public String getRepeating_mode() {
        return repeating_mode;
    }

    public int getAlarmId() { return alarmId; }

    public int getAlarmType() { return alarmType;}

    public boolean getActivated() {
        return activated;
    }
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public PresentableAlarmClockItem(int alarmId, int alarmType, String alarm_name, int hours, int minutes, String repeating_mode, boolean activated) {
        this.alarmId = alarmId;
        this.alarmType = alarmType;
        this.alarm_name = alarm_name;
        this.hours = hours;
        this.minutes = minutes;
        this.repeating_mode = repeating_mode;
        this.activated = activated;
    }

}
