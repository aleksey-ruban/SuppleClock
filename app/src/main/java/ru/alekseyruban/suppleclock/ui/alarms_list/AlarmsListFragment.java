package ru.alekseyruban.suppleclock.ui.alarms_list;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.alekseyruban.suppleclock.R;
import ru.alekseyruban.suppleclock.data.models.AlarmClockItem;
import ru.alekseyruban.suppleclock.data.repositories.AlarmItemsRepository;
import ru.alekseyruban.suppleclock.databinding.FragmentAlarmsListBinding;
import ru.alekseyruban.suppleclock.ui.adapters.AlarmRecyclerViewAdapter;

public class AlarmsListFragment extends Fragment {

    private FragmentAlarmsListBinding binding;
    private AlarmsListViewModel alarmsListViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        alarmsListViewModel = new ViewModelProvider(this).get(AlarmsListViewModel.class);

        binding = FragmentAlarmsListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.alarmsRecycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
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

        binding.alarmsRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.alarmsRecycleView.setAdapter(new AlarmRecyclerViewAdapter());

        alarmsListViewModel.getItems().observe(getViewLifecycleOwner(), (value) -> {
            ((AlarmRecyclerViewAdapter) binding.alarmsRecycleView.getAdapter()).updateData(value);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}