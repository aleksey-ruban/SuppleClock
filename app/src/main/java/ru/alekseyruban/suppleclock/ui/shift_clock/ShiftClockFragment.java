package ru.alekseyruban.suppleclock.ui.shift_clock;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.alekseyruban.suppleclock.R;
import ru.alekseyruban.suppleclock.databinding.FragmentShiftClockBinding;

public class ShiftClockFragment extends Fragment {

    private FragmentShiftClockBinding binding;
    private ShiftClockViewModel mViewModel;

    public static ShiftClockFragment newInstance() {
        return new ShiftClockFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentShiftClockBinding.inflate(inflater, container, false);

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ShiftClockViewModel.class);
        // TODO: Use the ViewModel
    }

}