package ru.alekseyruban.suppleclock.ui.alarms_list;

import ru.alekseyruban.suppleclock.data.models.PresentableAlarmClockItem;

public interface OnPresentableAlarmActionsListener {

    void onActivatedChanged(PresentableAlarmClockItem item);
    void onMoreDetailsClick(PresentableAlarmClockItem item);

}
