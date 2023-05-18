package ru.alekseyruban.suppleclock.ui.achievements;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.Objects;

import ru.alekseyruban.suppleclock.R;
import ru.alekseyruban.suppleclock.data.models.PresentableAlarmClockItem;
import ru.alekseyruban.suppleclock.databinding.FragmentAchievementsBinding;
import ru.alekseyruban.suppleclock.ui.adapters.AchievementsRecycleAdapter;
import ru.alekseyruban.suppleclock.ui.adapters.AlarmRecyclerViewAdapter;
import ru.alekseyruban.suppleclock.ui.alarms_list.OnPresentableAlarmActionsListener;

public class AchievementsFragment extends Fragment {

    private FragmentAchievementsBinding binding;

    private AchievementsViewModel achievementsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        achievementsViewModel = new ViewModelProvider(this).get(AchievementsViewModel.class);

        binding = FragmentAchievementsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.achievementsRecycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                outRect.bottom = (int) getResources().getDimension(R.dimen.activity_vertical_margin);
                outRect.left =  (int) getResources().getDimension(R.dimen.recycle_view_spacing_8dp);
                outRect.right =  (int) getResources().getDimension(R.dimen.recycle_view_spacing_8dp);
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.achievementsRecycleView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.achievementsRecycleView.setAdapter(new AchievementsRecycleAdapter(getContext()));

        achievementsViewModel.getDataSourceItems().observe(getViewLifecycleOwner(), (value) -> {
            ((AchievementsRecycleAdapter) Objects.requireNonNull(binding.achievementsRecycleView.getAdapter())).updateData(value);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}