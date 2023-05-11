package ru.alekseyruban.suppleclock.ui.alarms_list;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import ru.alekseyruban.suppleclock.R;
import ru.alekseyruban.suppleclock.data.models.PresentableAlarmClockItem;
import ru.alekseyruban.suppleclock.databinding.FragmentAlarmsListBinding;
import ru.alekseyruban.suppleclock.ui.MusicService;
import ru.alekseyruban.suppleclock.ui.adapters.AlarmRecyclerViewAdapter;

public class AlarmsListFragment extends Fragment {

    private FragmentAlarmsListBinding binding;
    private AlarmsListViewModel alarmsListViewModel;

    private BroadcastReceiver _broadcastReceiver;
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat _sdfWatchTime = new SimpleDateFormat("HH:mm");
    private TextView _tvTime;

    @SuppressLint("SetTextI18n")
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

                Navigation.findNavController(v).navigate(R.id.simpleClockFragment);


//                AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom));
//                dialog.setTitle(R.string.choose_alarm_type);
//                dialog.setItems(R.array.alarm_types, new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int position) {
//                        switch (position) {
//                            case 0:
//                                Navigation.findNavController(v).navigate(R.id.simpleClockFragment);
//                                break;
//                            case 1:
//                                Navigation.findNavController(v).navigate(R.id.shiftClockFragment);
//                                break;
//                            case 2:
//                                Navigation.findNavController(v).navigate(R.id.scheduleClockFragment);
//                                break;
//                        }
//                    }
//
//                });
//
//                AlertDialog alert = dialog.create();
//                alert.show();
            }
        });

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(cal.getTime());
        String day = new SimpleDateFormat("EEEE", Locale.getDefault()).format(new Date().getTime());
        day = day.substring(0, 1).toUpperCase() + day.substring(1);
        int dayNum = cal.get(Calendar.DAY_OF_MONTH);
        binding.dateTextView.setText(day + ", " + dayNum + " " + month_name);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.alarmsRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.alarmsRecycleView.setAdapter(new AlarmRecyclerViewAdapter(new OnPresentableAlarmActionsListener() {
            @Override
            public void onActivatedChanged(PresentableAlarmClockItem item) {

            }

            @Override
            public void onMoreDetailsClick(PresentableAlarmClockItem item) {
                Bundle bundle = new Bundle();
                bundle.putInt("alarm_id", item.getAlarmId());
                if (item.getAlarmType() == 0) {
                    Navigation.findNavController(view).navigate(R.id.simpleClockFragment, bundle);
                } else if (item.getAlarmType() == 1) {

                } else if (item.getAlarmType() == 2) {
                    Navigation.findNavController(view).navigate(R.id.scheduleClockFragment, bundle);
                }
            }
        }));

        _tvTime = binding.timeTextView;
        _tvTime.setText(_sdfWatchTime.format(new Date()));

        alarmsListViewModel.getDatabaseItems().observe(getViewLifecycleOwner(), (value) -> {
            ((AlarmRecyclerViewAdapter) Objects.requireNonNull(binding.alarmsRecycleView.getAdapter())).updateData(value);
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        _broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0)
                    _tvTime.setText(_sdfWatchTime.format(new Date()));
            }
        };

        requireActivity().registerReceiver(_broadcastReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));

    }

    @Override
    public void onStop() {
        super.onStop();
        if (_broadcastReceiver != null) {
            requireActivity().unregisterReceiver(_broadcastReceiver);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}