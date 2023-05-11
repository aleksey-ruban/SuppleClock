package ru.alekseyruban.suppleclock.ui.holding_solving;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import ru.alekseyruban.suppleclock.R;
import ru.alekseyruban.suppleclock.databinding.FragmentHoldingSolvingBinding;

public class HoldingSolvingFragment extends Fragment {

    private FragmentHoldingSolvingBinding binding;
    private HoldingSolvingViewModel mViewModel;

    private long timeDown;
    private long timeUp;

    public static HoldingSolvingFragment newInstance() {
        return new HoldingSolvingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHoldingSolvingBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(HoldingSolvingViewModel.class);

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });

        binding.delayAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Objects.requireNonNull(Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main).getPreviousBackStackEntry()).getSavedStateHandle().set("snooze", "1");
                Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        });

        Objects.requireNonNull(Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main).getCurrentBackStackEntry()).getSavedStateHandle().getLiveData("volume_down").observe(getViewLifecycleOwner(), o -> {
            timeDown = System.currentTimeMillis();
            timeUp = System.currentTimeMillis() + 5000;

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (timeUp - timeDown >= 3000) {
                        turnOff();
                    }
                }
            }, 3000);
        });

        Objects.requireNonNull(Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main).getCurrentBackStackEntry()).getSavedStateHandle().getLiveData("volume_up").observe(getViewLifecycleOwner(), o -> {
            timeUp = System.currentTimeMillis();
        });

        return binding.getRoot();
    }

    private void turnOff() {
        if (isAdded()) {
            Objects.requireNonNull(Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main).getPreviousBackStackEntry()).getSavedStateHandle().set("turn_off", "1");
            Navigation.findNavController(binding.getRoot()).popBackStack();
        }

    }



}