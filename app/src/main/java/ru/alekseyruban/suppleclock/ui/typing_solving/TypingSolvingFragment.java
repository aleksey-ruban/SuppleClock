package ru.alekseyruban.suppleclock.ui.typing_solving;

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
import ru.alekseyruban.suppleclock.databinding.FragmentTypingSolvingBinding;

public class TypingSolvingFragment extends Fragment {

    private FragmentTypingSolvingBinding binding;
    private TypingSolvingViewModel mViewModel;

    public static TypingSolvingFragment newInstance() {
        return new TypingSolvingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTypingSolvingBinding.inflate(inflater, container, false);

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
        mViewModel = new ViewModelProvider(this).get(TypingSolvingViewModel.class);
        // TODO: Use the ViewModel
    }

}