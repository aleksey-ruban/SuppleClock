package ru.alekseyruban.suppleclock.ui.calendar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import ru.alekseyruban.suppleclock.R;
import ru.alekseyruban.suppleclock.databinding.FragmentCalendarBinding;

public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding binding;

    private CalendarViewModel calendarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        CompactCalendarView compactCalendarView = binding.compactcalendarView;
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                Log.d("WORKING_IN_THREAD", "Day was clicked: " + dateClicked + " with events " + events);
                showDialogFragment();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                calendarViewModel.scrollSelectedMonth(firstDayOfNewMonth);
            }
        });

        calendarViewModel.selectedMonthContainer().observe(requireActivity(), selectedDate -> {
            binding.selectedMonth.setText(selectedDate);
        });

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.compactcalendarView.scrollRight();
            }
        });

        binding.prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.compactcalendarView.scrollLeft();
            }
        });

        binding.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.settingsFragment);

            }
        });

        binding.addAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom));
                dialog.setTitle(R.string.choose_alarm_type);
                dialog.setItems(R.array.alarm_types, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                Navigation.findNavController(v).navigate(R.id.simpleClockFragment);
                                break;
                            case 1:
                                Navigation.findNavController(v).navigate(R.id.shiftClockFragment);
                                break;
                            case 2:
                                Navigation.findNavController(v).navigate(R.id.scheduleClockFragment);
                                break;
                        }
                    }

                });

                AlertDialog alert = dialog.create();
                alert.show();
            }
        });




        
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showDialogFragment() {
        CalendarDaysDialog dialogFragment= new CalendarDaysDialog(calendarViewModel);
        dialogFragment.show(requireActivity().getSupportFragmentManager(), "My  Fragment");
    }
}