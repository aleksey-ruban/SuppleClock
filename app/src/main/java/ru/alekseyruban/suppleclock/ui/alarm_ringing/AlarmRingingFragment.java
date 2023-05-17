package ru.alekseyruban.suppleclock.ui.alarm_ringing;

import androidx.lifecycle.SavedStateViewModelFactory;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import ru.alekseyruban.suppleclock.R;
import ru.alekseyruban.suppleclock.data.data_sources.room.entites.AlarmCommonEntity;
import ru.alekseyruban.suppleclock.data.data_sources.room.entites.AlarmSimpleEntity;
import ru.alekseyruban.suppleclock.data.data_sources.room.root.AppDatabase;
import ru.alekseyruban.suppleclock.databinding.FragmentAlarmRingingBinding;
import ru.alekseyruban.suppleclock.ui.AlarmScheduler;
import ru.alekseyruban.suppleclock.ui.MusicService;

public class AlarmRingingFragment extends Fragment {

    private FragmentAlarmRingingBinding binding;
    private AlarmRingingViewModel mViewModel;

    private BroadcastReceiver _broadcastReceiver;
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat _sdfWatchTime = new SimpleDateFormat("HH:mm");
    private TextView _tvTime;

    private MusicService myService;
    private boolean isBound = false;

    private AlarmCommonEntity alarmCommonEntity;
    private AlarmSimpleEntity alarmSimpleEntity;

    private int delays;

    public static AlarmRingingFragment newInstance() {
        return new AlarmRingingFragment();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentAlarmRingingBinding.inflate(inflater, container, false);

        int commonId = getArguments() != null ? getArguments().getInt("common_id", -1) : -1;
        delays = getArguments() != null ? getArguments().getInt("delays", 0) : 0;

        ViewModelProvider.Factory factory = new SavedStateViewModelFactory(requireActivity().getApplication(), this, getArguments());
        mViewModel = new ViewModelProvider(this, new AlarmRingingViewModelFactory(requireActivity().getApplication(), commonId)).get(AlarmRingingViewModel.class);


        AppDatabase.databaseWriteExecutor.execute(() -> {
            AppDatabase databaseSource = AppDatabase.getDatabase(requireActivity().getApplication());
            alarmCommonEntity = databaseSource.alarmCommonDAO().getAlarmCommonById(commonId);

            if (alarmCommonEntity.alarmType == 0) {
                alarmSimpleEntity = databaseSource.alarmSimpleDAO().getAlarmSimpleWithCommonId(commonId);
            }

            Log.i("ALARM_CLOCK", "ALARM ID " + commonId);

            binding.alarmName.setText(alarmCommonEntity.name);
        });

        binding.turnOffAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOff();
            }
        });

        binding.delayAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snooze();
            }
        });

        _tvTime = binding.timeTextView;
        _tvTime.setText(_sdfWatchTime.format(new Date()));

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        @SuppressLint("SimpleDateFormat") SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        String month_name = month_date.format(cal.getTime());
        String day = new SimpleDateFormat("EEEE", Locale.getDefault()).format(new Date().getTime());
        day = day.substring(0, 1).toUpperCase() + day.substring(1);
        int dayNum = cal.get(Calendar.DAY_OF_MONTH);
        binding.dateTextView.setText(day + ", " + dayNum + " " + month_name);

        Objects.requireNonNull(Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main).getCurrentBackStackEntry()).getSavedStateHandle().getLiveData("snooze").observe(getViewLifecycleOwner(), o -> {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    snooze();
                }
            }, 500);
            Log.i("ALARM_CLOCK", "SNOOZING FORM SOLVING!!!");
        });

        Objects.requireNonNull(Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main).getCurrentBackStackEntry()).getSavedStateHandle().getLiveData("turn_off").observe(getViewLifecycleOwner(), o -> {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopService();
                }
            }, 500);
        });

        return binding.getRoot();
    }


    @Override
    public void onStart() {
        super.onStart();

        Intent intent = new Intent(requireActivity(), MusicService.class);
        requireActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

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
        if (isBound) {
            requireActivity().unbindService(serviceConnection);
            isBound = false;
        }
        if (_broadcastReceiver != null) {
            requireActivity().unregisterReceiver(_broadcastReceiver);
        }
    }


    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            MusicService.MyBinder myBinder = (MusicService.MyBinder) binder;
            myService = myBinder.getServiceSystem();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
        }
    };


    public void snooze() {
        if (delays == alarmCommonEntity.allowedDelays) {
            Toast.makeText(requireActivity().getApplicationContext(), getText(R.string.no_allowed_delays), Toast.LENGTH_LONG).show();
        } else {
            stopService();
            AlarmScheduler alarmScheduler = new AlarmScheduler(requireActivity().getApplication());
            alarmScheduler.snooze(alarmCommonEntity.commonId, delays + 1);
        }
    }

    public void turnOff() {
        if (alarmCommonEntity.necessarilyWakeup) {
            SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            int turn_off_type = sharedPref.getInt(getString(R.string.turn_off_type), 0);
            ArrayList<Integer> types = new ArrayList<>();
            types.add(R.id.typingSolvingFragment);
            types.add(R.id.shakingSolvingFragment);
            types.add(R.id.holdingSolvingFragment);

            Log.i("ALARM_CLOCK", "OFF TYPE IS " + turn_off_type);

            if (turn_off_type == 0) {
                Navigation.findNavController(binding.getRoot()).navigate(types.get(new Random().nextInt(types.size())));
            } else {
                Navigation.findNavController(binding.getRoot()).navigate(types.get(turn_off_type - 1));
            }


        } else {
            stopService();
        }

    }

    private void stopService() {
        if (isBound) {
            myService.stop();
            Navigation.findNavController(binding.getRoot()).popBackStack();

            if (alarmCommonEntity.alarmType == 0) {
                if (alarmSimpleEntity != null) {
                    int n = 0;
                    for (int i = 0; i < alarmSimpleEntity.alarmDays.size(); i++) {
                        if (alarmSimpleEntity.alarmDays.get(i)) { n++; }
                    }
                    if (n == 0) {
                        mViewModel.switchAlarmActive(alarmCommonEntity.commonId);
                    }
                }
            }

            SharedPreferences sharedPref = getContext().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.alarm_common_id), -1);
            editor.putInt(getString(R.string.alarm_delays_allowed), 0);
            editor.apply();
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("IS_BOUND", isBound);
    }

}