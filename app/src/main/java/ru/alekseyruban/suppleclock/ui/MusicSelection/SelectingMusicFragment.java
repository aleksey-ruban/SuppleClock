package ru.alekseyruban.suppleclock.ui.MusicSelection;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.alekseyruban.suppleclock.databinding.FragmentSelectingMusicBinding;
import ru.alekseyruban.suppleclock.ui.adapters.MusicRecycleAdapter;

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

        binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).popBackStack();
            }
        });

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