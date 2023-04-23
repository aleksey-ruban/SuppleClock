package ru.alekseyruban.suppleclock.ui.alarms_list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.alekseyruban.suppleclock.R;
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

        binding.settinsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.settingsFragment);

            }
        });

        binding.addAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom));
                dialog.setTitle(R.string.choose_alarm_type);
                dialog.setItems(R.array.alarm_types, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position) {
                            case 0:
                                Navigation.findNavController(v).navigate(R.id.simpleClockFragment);
                                break;
                            case 1:
                                Navigation.findNavController(v).navigate(R.id.shiftClockFragment);
                                break;
                            case 2:
                                Navigation.findNavController(v).navigate(R.id.scheduleClockFragment);
                                break;
                        }
                    }

                });

                AlertDialog alert = dialog.create();
                alert.show();
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