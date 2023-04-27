package ru.alekseyruban.suppleclock.ui.simpleClock;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
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
import ru.alekseyruban.suppleclock.data.models.AlarmSimpleItem;
import ru.alekseyruban.suppleclock.databinding.FragmentSimpleClockBinding;
import ru.alekseyruban.suppleclock.ui.alarm_details.AlarmDetails;

public class SimpleClockFragment extends Fragment {

    private FragmentSimpleClockBinding binding;
    private SimpleClockViewModel mViewModel;

    private int alarmEditingId;
    private boolean isCreatingNewAlarm;

    private Observer<AlarmSimpleItem> observer;

    public static SimpleClockFragment newInstance() {
        return new SimpleClockFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ViewModelProvider.Factory factory = new SavedStateViewModelFactory(requireActivity().getApplication(), this, getArguments());
        mViewModel = new ViewModelProvider(this, factory).get(SimpleClockViewModel.class);

        binding = FragmentSimpleClockBinding.inflate(inflater, container, false);

        alarmEditingId = getArguments() != null ? getArguments().getInt("alarm_id", -1) : -1;
        isCreatingNewAlarm = alarmEditingId == -1;
        if (!isCreatingNewAlarm) {
            mViewModel.setEditingAlarmId(alarmEditingId);
        }

        binding.timePicker.setIs24HourView(true);

        binding.backButton.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        binding.mondayButton.setOnClickListener(v -> mViewModel.selectDay(0));
        binding.tuesdayButton.setOnClickListener(v -> mViewModel.selectDay(1));
        binding.wednesdayButton.setOnClickListener(v -> mViewModel.selectDay(2));
        binding.thursdayButton.setOnClickListener(v -> mViewModel.selectDay(3));
        binding.fridayButton.setOnClickListener(v -> mViewModel.selectDay(4));
        binding.saturdayButton.setOnClickListener(v -> mViewModel.selectDay(5));
        binding.sundayButton.setOnClickListener(v -> mViewModel.selectDay(6));

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmDetails alarmDetails = binding.detailsFragment.getFragment();
                if (isCreatingNewAlarm) {
                    mViewModel.saveAlarm(alarmDetails.getAlarmCommonInfo());
                } else {
                    mViewModel.updateAlarm(alarmDetails.getAlarmCommonInfo());
                }
                Navigation.findNavController(v).popBackStack();
            }
        });

        binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCreatingNewAlarm) {
                    mViewModel.deleteAlarmSimple(alarmEditingId);
                }
                Navigation.findNavController(v).popBackStack();
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

        mViewModel.changeTime(binding.timePicker.getHour(), binding.timePicker.getMinute());

        if (!isCreatingNewAlarm) {
            setupEditingValues();
        }

        return binding.getRoot();
    }

    private void setupEditingValues() {

        mViewModel.getEditingAlarm(alarmEditingId);

        observer = new Observer<AlarmSimpleItem>() {
            @Override
            public void onChanged(AlarmSimpleItem alarmSimpleItem) {
                if (!isAdded()) { return; }

                binding.timePicker.setHour(Objects.requireNonNull(alarmSimpleItem).getHours());
                binding.timePicker.setMinute(alarmSimpleItem.getMinutes());

                ArrayList<Button> buttons = new ArrayList<>();
                buttons.add(binding.mondayButton);
                buttons.add(binding.tuesdayButton);
                buttons.add(binding.wednesdayButton);
                buttons.add(binding.thursdayButton);
                buttons.add(binding.fridayButton);
                buttons.add(binding.saturdayButton);
                buttons.add(binding.sundayButton);
                for (int i = 0; i < alarmSimpleItem.getAlarmDays().size(); i++) {
                    buttons.get(i).setSelected(alarmSimpleItem.getAlarmDays().get(i));
                    if(buttons.get(i).isSelected()){
                        buttons.get(i).setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.button_blue_radius8));
                    }else{
                        buttons.get(i).setBackground(ContextCompat.getDrawable(requireActivity(), R.drawable.button_radius8));
                    }
                }

                AlarmDetails alarmDetails = binding.detailsFragment.getFragment();
                alarmDetails.setAlarmCommonInfo(alarmSimpleItem);

                mViewModel.updateInfoToEditing();

                mViewModel.getEditingAlarmSimple().removeObserver(observer);

            }
        };

        mViewModel.getEditingAlarmSimple().observe(requireActivity(), observer);
    }

}