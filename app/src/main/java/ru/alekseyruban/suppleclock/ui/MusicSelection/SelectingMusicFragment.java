package ru.alekseyruban.suppleclock.ui.MusicSelection;

import androidx.lifecycle.ViewModelProvider;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.alekseyruban.suppleclock.R;
import ru.alekseyruban.suppleclock.databinding.FragmentSelectingMusicBinding;
import ru.alekseyruban.suppleclock.ui.adapters.CalendarRecycleAdapter;
import ru.alekseyruban.suppleclock.ui.adapters.MusicRecycleAdapter;
import ru.alekseyruban.suppleclock.ui.calendar.CalendarViewModel;

public class SelectingMusicFragment extends Fragment {

    public static SelectingMusicFragment newInstance() {
        return new SelectingMusicFragment();
    }

    private FragmentSelectingMusicBinding binding;

    private SelectingMusicViewModel musicViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentSelectingMusicBinding.inflate(inflater, container, false);

        musicViewModel = new ViewModelProvider(this).get(SelectingMusicViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.musicRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.musicRecycleView.setAdapter(new MusicRecycleAdapter());

        musicViewModel.getItems().observe(getViewLifecycleOwner(), (value) -> {
            ((MusicRecycleAdapter) binding.musicRecycleView.getAdapter()).updateData(value);
        });
    }

}