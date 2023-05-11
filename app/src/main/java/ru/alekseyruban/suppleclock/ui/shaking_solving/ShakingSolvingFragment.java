package ru.alekseyruban.suppleclock.ui.shaking_solving;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventCallback;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Objects;

import ru.alekseyruban.suppleclock.R;
import ru.alekseyruban.suppleclock.databinding.FragmentShakingSolvingBinding;

public class ShakingSolvingFragment extends Fragment {

    private FragmentShakingSolvingBinding binding;
    private ShakingSolvingViewModel mViewModel;

    private SensorManager sensorMgr;
    private SensorEventCallback callback;

    private long lastUpdate;
    private float last_x;
    private float last_y;
    private float last_z;
    private static final int SHAKE_THRESHOLD = 800;

    public static ShakingSolvingFragment newInstance() {
        return new ShakingSolvingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentShakingSolvingBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(ShakingSolvingViewModel.class);

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

        sensorMgr = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);

        callback = new SensorEventCallback() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                super.onSensorChanged(event);
                final int type = event.sensor.getType();
                if (type == Sensor.TYPE_ACCELEROMETER) {
                    long curTime = System.currentTimeMillis();
                    // only allow one update every 100ms.
                    if ((curTime - lastUpdate) > 100) {
                        long diffTime = (curTime - lastUpdate);
                        lastUpdate = curTime;

                        float x = event.values[0];
                        float y = event.values[1];
                        float z = event.values[2];

                        float speed = Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;

                        if (speed > SHAKE_THRESHOLD) {
                            Log.d("sensor", "shake detected w/ speed: " + speed);
                            turnOff();
                        }
                        last_x = x;
                        last_y = y;
                        last_z = z;
                    }
                }
            }
        };

        return binding.getRoot();
    }

    private void turnOff() {
        Objects.requireNonNull(Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main).getPreviousBackStackEntry()).getSavedStateHandle().set("turn_off", "1");
        Navigation.findNavController(binding.getRoot()).popBackStack();
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorMgr.registerListener(callback, sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME );
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorMgr.unregisterListener(callback);
    }
}