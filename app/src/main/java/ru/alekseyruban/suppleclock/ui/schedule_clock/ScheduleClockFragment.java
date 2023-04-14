package ru.alekseyruban.suppleclock.ui.schedule_clock;

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
import ru.alekseyruban.suppleclock.databinding.FragmentScheduleClockBinding;
import ru.alekseyruban.suppleclock.ui.adapters.ScheduledWeekAdapter;

public class ScheduleClockFragment extends Fragment {

    private ScheduleClockViewModel mViewModel;
    private FragmentScheduleClockBinding binding;

    public static ScheduleClockFragment newInstance() {
        return new ScheduleClockFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(ScheduleClockViewModel.class);

        binding = FragmentScheduleClockBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.scheduledAlarmRecycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                outRect.bottom = (int) getResources().getDimension(R.dimen.recycle_view_spacing_14dp);
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.scheduledAlarmRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.scheduledAlarmRecycleView.setAdapter(new ScheduledWeekAdapter());

        mViewModel.getItems().observe(getViewLifecycleOwner(), (value) -> {
            ((ScheduledWeekAdapter) binding.scheduledAlarmRecycleView.getAdapter()).updateData(value);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}