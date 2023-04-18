package ru.alekseyruban.suppleclock.ui.calendar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.Date;
import java.util.List;

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
                Log.d("WORKING_IN_THREAD", "Month was scrolled to: " + firstDayOfNewMonth);
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