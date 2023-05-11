package ru.alekseyruban.suppleclock.ui.calendar;

import android.app.Dialog;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import ru.alekseyruban.suppleclock.R;
import ru.alekseyruban.suppleclock.data.models.PresentableAlarmClockItem;
import ru.alekseyruban.suppleclock.databinding.CalendarDialogBinding;
import ru.alekseyruban.suppleclock.ui.adapters.AlarmRecyclerViewAdapter;
import ru.alekseyruban.suppleclock.ui.adapters.CalendarRecycleAdapter;
import ru.alekseyruban.suppleclock.ui.alarms_list.OnPresentableAlarmActionsListener;

public class CalendarDaysDialog extends DialogFragment {

    private CalendarDialogBinding binding;

    private CalendarViewModel calendarViewModel;

    public CalendarDaysDialog() {
        super();
    }

    public CalendarDaysDialog(CalendarViewModel calendarViewModel) {
        super();
        this.calendarViewModel = calendarViewModel;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = CalendarDialogBinding.inflate(inflater, container, false);

        binding.alarmsRecycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                outRect.top = (int) getResources().getDimension(R.dimen.recycle_view_spacing_8dp);
            }
        });

        binding.closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.alarmsRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.alarmsRecycleView.setAdapter(new CalendarRecycleAdapter(new OnPresentableAlarmActionsListener() {
            @Override
            public void onActivatedChanged(PresentableAlarmClockItem item) {
                if (!calendarViewModel.getWasSwitchedActive()) {
                    calendarViewModel.switchAlarmActive(item.getAlarmId());
                }
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            sleep(2000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        calendarViewModel.setWasSwitchedActive(true);
                    }
                }.start();
            }

            @Override
            public void onMoreDetailsClick(PresentableAlarmClockItem item) {
                calendarViewModel.setWasOpenedToEdit(false);
                calendarViewModel.setAlarmIdToChange(item);
                dismiss();
            }
        }));


        calendarViewModel.getDatabaseItems(calendarViewModel.selectedDate).observe(getViewLifecycleOwner(), (value) -> {

            ((CalendarRecycleAdapter) Objects.requireNonNull(binding.alarmsRecycleView.getAdapter())).updateData(value);

        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            // set "origin" to top left corner
            dialog.getWindow().setGravity(Gravity.BOTTOM);

            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();

            dialog.getWindow().setAttributes(params);
        }
    }

}
