package ru.alekseyruban.suppleclock.ui.settings;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TimePicker;

import java.util.Arrays;
import java.util.Objects;

import ru.alekseyruban.suppleclock.R;
import ru.alekseyruban.suppleclock.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private SettingsViewModel mViewModel;


    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });

        SharedPreferences sharedPref = requireContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        boolean notification = sharedPref.getBoolean(getString(R.string.sleep_notification), false);
        int hoursTarget = sharedPref.getInt(getString(R.string.sleep_hours_target), 8);
        int minutesTarget = sharedPref.getInt(getString(R.string.sleep_minutes_target), 0);
        int restBeforeSleep = sharedPref.getInt(getString(R.string.rest_before_sleep), 30);
        int morningTime = sharedPref.getInt(getString(R.string.extra_morning_time), 30);
        int turn_off_type = sharedPref.getInt(getString(R.string.turn_off_type), 0);

        binding.remindSwitch.setChecked(notification);
        binding.remindSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(getString(R.string.sleep_notification), isChecked);
                editor.apply();
            }
        });

        binding.sleepTarget.setText((hoursTarget == 0 ? "00" : hoursTarget) + " ч " + (minutesTarget < 10 ? "0" + minutesTarget : minutesTarget) + " мин");
        binding.eveningChill.setText(restBeforeSleep + " мин");
        binding.extraTimeTextView.setText(morningTime + " мин");

        binding.solveWayTextView.setText(Arrays.asList(getResources().getStringArray(R.array.turn_off_type)).get(turn_off_type));
        binding.solveWayTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom));
                dialog.setTitle(R.string.choose_turn_off_type);
                dialog.setItems(R.array.turn_off_type, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        binding.solveWayTextView.setText(Arrays.asList(getResources().getStringArray(R.array.turn_off_type)).get(position));
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt(getString(R.string.turn_off_type), position);
                        editor.apply();
                    }

                });

                AlertDialog alert = dialog.create();
                alert.show();
            }
        });

        binding.sleepTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpTimePicker(0);
            }
        });

        binding.eveningChill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpTimePicker(1);
            }
        });

        binding.extraTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpTimePicker(2);
            }
        });

        return binding.getRoot();
    }

    private void popUpTimePicker(int num) {

        SharedPreferences sharedPref = requireContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                SharedPreferences.Editor editor = sharedPref.edit();

                if (num == 0) {
                    editor.putInt(getString(R.string.sleep_hours_target), hourOfDay);
                    editor.putInt(getString(R.string.sleep_minutes_target), minute);
                    binding.sleepTarget.setText((hourOfDay == 0 ? "00" : hourOfDay) + " ч " + (minute < 10 ? "0" + minute : minute) + " мин");
                } else if (num == 1) {
                    editor.putInt(getString(R.string.rest_before_sleep), hourOfDay * 60 + minute);
                    binding.eveningChill.setText(hourOfDay * 60 + minute + " мин");
                } else if (num == 2 ) {
                    editor.putInt(getString(R.string.extra_morning_time), hourOfDay * 60 + minute);
                    binding.extraTimeTextView.setText(hourOfDay * 60 + minute + " мин");
                }

                editor.apply();

            }
        };
        int hoursTarget = 0;
        int minutesTarget = 0;

        if (num == 0) {
            hoursTarget = sharedPref.getInt(getString(R.string.sleep_hours_target), 8);
            minutesTarget = sharedPref.getInt(getString(R.string.sleep_minutes_target), 0);
        } else if (num == 1) {
            hoursTarget = sharedPref.getInt(getString(R.string.rest_before_sleep), 30) / 60;
            minutesTarget = sharedPref.getInt(getString(R.string.rest_before_sleep), 30) % 60;
        } else if (num == 2) {
            hoursTarget = sharedPref.getInt(getString(R.string.extra_morning_time), 30) / 60;
            minutesTarget = sharedPref.getInt(getString(R.string.extra_morning_time), 30) % 60;
        }
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), AlertDialog.THEME_HOLO_DARK, onTimeSetListener, hoursTarget, minutesTarget, true);
        timePickerDialog.setTitle(R.string.select_time);

        timePickerDialog.show();
    }


}