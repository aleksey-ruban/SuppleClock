package ru.alekseyruban.suppleclock.ui.shaking_solving;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.alekseyruban.suppleclock.R;

public class ShakingSolvingFragment extends Fragment {

    private ShakingSolvingViewModel mViewModel;

    public static ShakingSolvingFragment newInstance() {
        return new ShakingSolvingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shaking_solving, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ShakingSolvingViewModel.class);
        // TODO: Use the ViewModel
    }

}