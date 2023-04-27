package ru.alekseyruban.suppleclock.data.models;

import java.util.ArrayList;

public class AlarmCommonItem {

//    private int commonId;
//    public int getCommonId() { return commonId;}

    private String name;
    public String getName() {return name;}

    private boolean activated;
    public boolean isActivated() {return activated;}

    private int allowedDelays;
    public int getAllowedDelays() {return allowedDelays;}

    private boolean necessarilyWakeup;
    public boolean isNecessarilyWakeup() {return necessarilyWakeup;}

    private boolean morningTime;
    public boolean isMorningTime() {return morningTime;}

    private boolean defaultMusic;
    public boolean isDefaultMusic() {return defaultMusic;}

    private ArrayList<String> music;
    public ArrayList<String> getMusic() {return music;}

    public AlarmCommonItem() { }

    public AlarmCommonItem(String name, int allowedDelays, boolean necessarilyWakeup, boolean morningTime, boolean defaultMusic, ArrayList<String> music) {
        this.name = name;
        this.activated = true;
        this.allowedDelays = allowedDelays;
        this.necessarilyWakeup = necessarilyWakeup;
        this.morningTime = morningTime;
        this.defaultMusic = defaultMusic;
        this.music = music;
    }

}
