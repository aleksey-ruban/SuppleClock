package ru.alekseyruban.suppleclock.ui.typing_solving;

import androidx.lifecycle.ViewModelProvider;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;
import java.util.Random;

import ru.alekseyruban.suppleclock.R;
import ru.alekseyruban.suppleclock.databinding.FragmentTypingSolvingBinding;

public class TypingSolvingFragment extends Fragment {

    private FragmentTypingSolvingBinding binding;
    private TypingSolvingViewModel mViewModel;

    public static TypingSolvingFragment newInstance() {
        return new TypingSolvingFragment();
    }

    private static final String[] phrases = {"Доброе утро", "Сегодня отличный день",
            "Превратите боль в силу", "Ты великолепен", "Оставайся сильным", "Сегодня выбираю радость",
            "Мелочи делают большие дни", "Помни почему ты начал", "Забота о себе продуктивна",
            "Просыпайтесь с решимостью", "Гладкое море никогда не делало умелого моряка"};

    private String currentPhrase;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTypingSolvingBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(TypingSolvingViewModel.class);

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

        currentPhrase = phrases[new Random().nextInt(phrases.length)];
        binding.typeSthTextView.setText("Введите \"" + currentPhrase + "\" для отключения будильника");

        binding.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals(currentPhrase)) {
                    turnOff();
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });

        return binding.getRoot();
    }

    private void turnOff() {
        Objects.requireNonNull(Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main).getPreviousBackStackEntry()).getSavedStateHandle().set("turn_off", "1");
        Navigation.findNavController(binding.getRoot()).popBackStack();
    }


}