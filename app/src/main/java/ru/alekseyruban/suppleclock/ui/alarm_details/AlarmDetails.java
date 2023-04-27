package ru.alekseyruban.suppleclock.ui.alarm_details;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import ru.alekseyruban.suppleclock.R;
import ru.alekseyruban.suppleclock.data.models.AlarmCommonItem;
import ru.alekseyruban.suppleclock.databinding.FragmentAlarmDetailsBinding;

public class AlarmDetails extends Fragment {

    private FragmentAlarmDetailsBinding binding;
    private AlarmDetailsViewModel mViewModel;

    public static AlarmDetails newInstance() {
        return new AlarmDetails();
    }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(AlarmDetailsViewModel.class);
        binding = FragmentAlarmDetailsBinding.inflate(inflater, container, false);

        binding.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.changeAllowedDelays(false);
            }
        });

        binding.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.changeAllowedDelays(true);
            }
        });

        mViewModel.allowedDelaysContainer().observe(requireActivity(), allowedDelays -> {
            String word;
            if (allowedDelays % 10 == 1 && allowedDelays % 100 != 11) {
                word = "раз";
            } else if (allowedDelays % 10 >= 2 && allowedDelays % 10 <= 4 && (allowedDelays % 100 < 10 || allowedDelays % 100 >= 20)) {
                word = "раза";
            } else {
                word = "раз";
            }
            binding.delayNumber.setText(allowedDelays + " " + word);
        });

        binding.delayNumber.setText(mViewModel.getAllowedDelays());

        binding.necessarilyWakeupSwitch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setClickable(false);
                mViewModel.switchNecessarilyWakeup();
                return false;
            }
        });

        binding.morningTimeSwitch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setClickable(false);
                mViewModel.switchMorningTime();
                return false;
            }
        });

        binding.alarmName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mViewModel.changeName(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        mViewModel.necessarilyWakeup().observe(requireActivity(), b -> {
            binding.necessarilyWakeupSwitch.setChecked(b);
        });

        mViewModel.morningTime().observe(requireActivity(), b -> {
            binding.morningTimeSwitch.setChecked(b);
        });

        return binding.getRoot();
    }

    public AlarmCommonItem getAlarmCommonInfo() {
        return mViewModel.formAlarmCommon();
    }

    public void setAlarmCommonInfo(AlarmCommonItem item) {
        mViewModel.setCommonInfo(item);
        if (item.getName().equals("Будильник")) {
            binding.alarmName.setText("");
        } else {
            binding.alarmName.setText(item.getName());
        }
    }
}