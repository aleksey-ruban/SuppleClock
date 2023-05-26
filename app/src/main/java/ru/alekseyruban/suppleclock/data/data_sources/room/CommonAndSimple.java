package ru.alekseyruban.suppleclock.data.data_sources.room;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.ArrayList;

import ru.alekseyruban.suppleclock.data.data_sources.room.entites.AlarmCommonEntity;
import ru.alekseyruban.suppleclock.data.data_sources.room.entites.AlarmSimpleEntity;
import ru.alekseyruban.suppleclock.data.models.AlarmSimpleItem;
import ru.alekseyruban.suppleclock.data.models.PresentableAlarmClockItem;

public class CommonAndSimple {

    @Embedded public AlarmCommonEntity alarmCommon;
    @Relation(parentColumn = "commonId", entityColumn = "alarmCommonId")
    public AlarmSimpleEntity alarmSimple;

    public PresentableAlarmClockItem toDomainModel() {
        ArrayList<String> daysN = new ArrayList<>();
        daysN.add("Пн"); daysN.add("Вт"); daysN.add("Ср"); daysN.add("Чт");
        daysN.add("Пт"); daysN.add("Сб"); daysN.add("Вс");
        StringBuilder repMode = new StringBuilder();
        int n = 0;

        for (int i = 0; i < alarmSimple.alarmDays.size(); i++) {
            if (alarmSimple.alarmDays.get(i)) {
                repMode.append(daysN.get(i)).append(" ");
                n++;
            }
        }
        if (n == 7) {
            repMode.delete(0, repMode.length() - 1);
            repMode.append("Каждый день");
        }
        if (repMode.length() == 0) {
            repMode.append("Единоразовый");
        }

        return new PresentableAlarmClockItem(alarmCommon.commonId, alarmCommon.alarmType, alarmCommon.name, alarmSimple.hours, alarmSimple.minutes, repMode.toString(), alarmCommon.activated);
    }

    public AlarmSimpleItem toSimpleModel() {
        return new AlarmSimpleItem(alarmCommon.name, alarmCommon.allowedDelays, alarmCommon.necessarilyWakeup, alarmCommon.morningTime, alarmCommon.defaultMusic, alarmCommon.music, alarmSimple.hours, alarmSimple.minutes, alarmSimple.alarmDays, alarmSimple.colorNumber);
    }
}
