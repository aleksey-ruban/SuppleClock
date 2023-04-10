package ru.alekseyruban.suppleclock.ui.alarms_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.alekseyruban.suppleclock.databinding.FragmentAlarmsListBinding;

public class AlarmsListFragment extends Fragment {

    private FragmentAlarmsListBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AlarmsListViewModel alarmsListViewModel =
                new ViewModelProvider(this).get(AlarmsListViewModel.class);

        binding = FragmentAlarmsListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        alarmsListViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}