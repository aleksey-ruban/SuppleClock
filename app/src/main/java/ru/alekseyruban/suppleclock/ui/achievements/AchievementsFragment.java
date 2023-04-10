package ru.alekseyruban.suppleclock.ui.achievements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.alekseyruban.suppleclock.databinding.FragmentAchievementsBinding;

public class AchievementsFragment extends Fragment {

    private FragmentAchievementsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AchievementsViewModel achievementsViewModel =
                new ViewModelProvider(this).get(AchievementsViewModel.class);

        binding = FragmentAchievementsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        achievementsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}