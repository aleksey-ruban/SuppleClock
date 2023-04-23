package ru.alekseyruban.suppleclock.ui.holding_solving;

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
import ru.alekseyruban.suppleclock.databinding.FragmentHoldingSolvingBinding;

public class HoldingSolvingFragment extends Fragment {

    private FragmentHoldingSolvingBinding binding;
    private HoldingSolvingViewModel mViewModel;

    public static HoldingSolvingFragment newInstance() {
        return new HoldingSolvingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentHoldingSolvingBinding.inflate(inflater, container, false);

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
        mViewModel = new ViewModelProvider(this).get(HoldingSolvingViewModel.class);
        // TODO: Use the ViewModel
    }

}