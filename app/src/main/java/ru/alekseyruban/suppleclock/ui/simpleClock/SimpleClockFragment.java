package ru.alekseyruban.suppleclock.ui.simpleClock;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.alekseyruban.suppleclock.R;

public class SimpleClockFragment extends Fragment {

    private SimpleClockViewModel mViewModel;

    public static SimpleClockFragment newInstance() {
        return new SimpleClockFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_simple_clock, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SimpleClockViewModel.class);
        // TODO: Use the ViewModel
    }

}