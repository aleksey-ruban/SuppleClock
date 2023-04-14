package ru.alekseyruban.suppleclock.data.models;

import java.util.ArrayList;

public class ScheduledWeekItem {
    private ArrayList<Integer> wakingUpHours;
    private ArrayList<Integer> wakingUpMinutes;
    private ArrayList<Integer> holidays;

    public ScheduledWeekItem(ArrayList<Integer> wakingUpHours, ArrayList<Integer> wakingUpMinutes, ArrayList<Integer> holidays) {
        this.wakingUpHours = wakingUpHours;
        this.wakingUpMinutes = wakingUpMinutes;
        this.holidays = holidays;
    }

    public ArrayList<Integer> getWakingUpHours() {
        return wakingUpHours;
    }

    public ArrayList<Integer> getWakingUpMinutes() {
        return wakingUpMinutes;
    }

    public ArrayList<Integer> getHolidays() {
        return holidays;
    }

}
