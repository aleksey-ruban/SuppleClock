package ru.alekseyruban.suppleclock.ui.alarm_ringing;

import androidx.lifecycle.Observer;
import androidx.lifecycle.SavedStateViewModelFactory;
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
import ru.alekseyruban.suppleclock.data.data_sources.room.entites.AlarmCommonEntity;
import ru.alekseyruban.suppleclock.data.repositories.AlarmItemsRepository;
import ru.alekseyruban.suppleclock.databinding.FragmentAlarmRingingBinding;
import ru.alekseyruban.suppleclock.ui.simpleClock.SimpleClockViewModel;

public class AlarmRingingFragment extends Fragment {

    private FragmentAlarmRingingBinding binding;
    private AlarmRingingViewModel mViewModel;



    public static AlarmRingingFragment newInstance() {
        return new AlarmRingingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentAlarmRingingBinding.inflate(inflater, container, false);

        int commonId = getArguments() != null ? getArguments().getInt("common_id", -1) : -1;
        ViewModelProvider.Factory factory = new SavedStateViewModelFactory(requireActivity().getApplication(), this, getArguments());
        mViewModel = new ViewModelProvider(this, new AlarmRingingViewModelFactory(requireActivity().getApplication(), commonId)).get(AlarmRingingViewModel.class);

        mViewModel.alarmCommonEntity().observe(getViewLifecycleOwner(), new Observer<AlarmCommonEntity>() {
            @Override
            public void onChanged(AlarmCommonEntity alarmCommonEntity) {
                binding.alarmName.setText(alarmCommonEntity.name);
            }
        });

        return binding.getRoot();
    }


}