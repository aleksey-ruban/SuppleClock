package ru.alekseyruban.suppleclock.ui.schedule_clock;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import ru.alekseyruban.suppleclock.R;
import ru.alekseyruban.suppleclock.databinding.FragmentAlarmsListBinding;
import ru.alekseyruban.suppleclock.databinding.FragmentScheduledClockDetailsBinding;

public class ScheduledClockDetailsFragment extends Fragment {

    private FragmentScheduledClockDetailsBinding binding;

    private ScheduledClockDetailsViewModel mViewModel;

    public static ScheduledClockDetailsFragment newInstance() {
        return new ScheduledClockDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentScheduledClockDetailsBinding.inflate(inflater, container, false);

        binding.monday.setOnClickListener(v -> showDialogFragment(0));
        binding.tuesday.setOnClickListener(v -> showDialogFragment(1));
        binding.wednesday.setOnClickListener(v -> showDialogFragment(2));
        binding.thursday.setOnClickListener(v -> showDialogFragment(3));
        binding.friday.setOnClickListener(v -> showDialogFragment(4));
        binding.saturday.setOnClickListener(v -> showDialogFragment(5));
        binding.sunday.setOnClickListener(v -> showDialogFragment(6));

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ScheduledClockDetailsViewModel.class);
        // TODO: Use the ViewModel
    }

    private void showDialogFragment(int number) {
        ScheduledWeekDialog dialogFragment=new ScheduledWeekDialog();
        dialogFragment.show(requireActivity().getSupportFragmentManager(), "My  Fragment");
    }

}