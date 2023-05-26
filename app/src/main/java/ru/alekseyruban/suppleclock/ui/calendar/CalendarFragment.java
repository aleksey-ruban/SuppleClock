package ru.alekseyruban.suppleclock.ui.calendar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.Date;
import java.util.List;

import ru.alekseyruban.suppleclock.R;
import ru.alekseyruban.suppleclock.data.models.PresentableAlarmClockItem;
import ru.alekseyruban.suppleclock.databinding.FragmentCalendarBinding;

public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding binding;

    private CalendarViewModel calendarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ViewModelProvider.Factory factory = new SavedStateViewModelFactory(requireActivity().getApplication(), this, getArguments());
        calendarViewModel = new ViewModelProvider(this, factory).get(CalendarViewModel.class);

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        CompactCalendarView compactCalendarView = binding.compactcalendarView;
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                List<Event> events = compactCalendarView.getEvents(dateClicked);
                calendarViewModel.selectedDate = dateClicked;
                showDialogFragment();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                calendarViewModel.scrollSelectedMonth(firstDayOfNewMonth);
            }
        });

        calendarViewModel.alarmItemToChange().observe(requireActivity(), new Observer<PresentableAlarmClockItem>() {
            @Override
            public void onChanged(PresentableAlarmClockItem item) {
                Bundle bundle = new Bundle();
                bundle.putInt("alarm_id", item.getAlarmId());
                if (!calendarViewModel.getWasOpenedToEdit()) {
                    if (item.getAlarmType() == 0) {
                        Navigation.findNavController(binding.getRoot()).navigate(R.id.simpleClockFragment, bundle);
                    } else if (item.getAlarmType() == 1) {

                    } else if (item.getAlarmType() == 2) {
                        Navigation.findNavController(binding.getRoot()).navigate(R.id.scheduleClockFragment, bundle);
                    }
                    calendarViewModel.setWasOpenedToEdit(true);
                }
            }
        });

        calendarViewModel.selectedMonthContainer().observe(requireActivity(), selectedDate -> {
            binding.selectedMonth.setText(selectedDate);
        });

        calendarViewModel.scrollSelectedMonth(compactCalendarView.getFirstDayOfCurrentMonth());
        binding.selectedMonth.setText(calendarViewModel.selectedMonthContainer().getValue());

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
                Navigation.findNavController(v).navigate(R.id.simpleClockFragment);
            }
        });

        return root;
    }

    private void showDialogFragment() {
        CalendarDaysDialog dialogFragment= new CalendarDaysDialog(calendarViewModel);
        dialogFragment.show(requireActivity().getSupportFragmentManager(), "My  Fragment");
    }
}