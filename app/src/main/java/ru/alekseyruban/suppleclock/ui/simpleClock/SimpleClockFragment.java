package ru.alekseyruban.suppleclock.ui.simpleClock;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Objects;

import ru.alekseyruban.suppleclock.R;
import ru.alekseyruban.suppleclock.databinding.FragmentSimpleClockBinding;

public class SimpleClockFragment extends Fragment {

    private FragmentSimpleClockBinding binding;
    private SimpleClockViewModel mViewModel;

    public static SimpleClockFragment newInstance() {
        return new SimpleClockFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(SimpleClockViewModel.class);
        binding = FragmentSimpleClockBinding.inflate(inflater, container, false);

        binding.timePicker.setIs24HourView(true);

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });

        binding.mondayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.selectDay(0);
            }
        });

        binding.tuesdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.selectDay(1);
            }
        });

        binding.wednesdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.selectDay(2);
            }
        });

        binding.thursdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.selectDay(3);
            }
        });

        binding.fridayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.selectDay(4);
            }
        });

        binding.saturdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.selectDay(5);
            }
        });

        binding.sundayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.selectDay(6);
            }
        });

        mViewModel.selectedDaysContainer().observe(requireActivity(), selectedDays -> {
            ArrayList<Button> buttons = new ArrayList<>();
            buttons.add(binding.mondayButton);
            buttons.add(binding.tuesdayButton);
            buttons.add(binding.wednesdayButton);
            buttons.add(binding.thursdayButton);
            buttons.add(binding.fridayButton);
            buttons.add(binding.saturdayButton);
            buttons.add(binding.sundayButton);
            for (int i = 0; i < selectedDays.size(); i++) {
                buttons.get(i).setSelected(selectedDays.get(i));
                if(buttons.get(i).isSelected()){
                    buttons.get(i).setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.button_blue_radius8));
                }else{
                    buttons.get(i).setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.button_radius8));
                }
            }
        });

        binding.timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mViewModel.changeTime(hourOfDay, minute);
            }
        });



        return binding.getRoot();
    }

}