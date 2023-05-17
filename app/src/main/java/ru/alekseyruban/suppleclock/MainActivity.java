package ru.alekseyruban.suppleclock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Objects;

import ru.alekseyruban.suppleclock.databinding.ActivityMainBinding;
import ru.alekseyruban.suppleclock.ui.NotificationService;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("ALARM_CLOCK", "MAIN ACTIVITY");

        Objects.requireNonNull(getSupportActionBar()).hide();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_alarm_list, R.id.navigation_calendar, R.id.navigation_achievements)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if(navDestination.getId() == R.id.navigation_alarm_list || navDestination.getId() == R.id.navigation_calendar || navDestination.getId() == R.id.navigation_achievements) {
                    navView.setVisibility(View.VISIBLE);
                }else{
                    navView.setVisibility(View.GONE);
                }
            }
        });

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        int common_id = sharedPref.getInt(getString(R.string.alarm_common_id), -1);

        int activeAlarmCommonId = getIntent().getIntExtra("active_alarm_common_id", -1);
        if (activeAlarmCommonId > -1) {
            Bundle bundle = new Bundle();
            bundle.putInt("common_id", activeAlarmCommonId);
            bundle.putInt("delays", getIntent().getIntExtra("delays", 0));
            navController.navigate(R.id.alarmRingingFragment, bundle);
        } else if (common_id > -1) {
            int delays = sharedPref.getInt(getString(R.string.alarm_delays_allowed), 0);
            Bundle bundle = new Bundle();
            bundle.putInt("common_id", common_id);
            bundle.putInt("delays", delays);
            navController.navigate(R.id.alarmRingingFragment, bundle);

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.alarm_common_id), -1);
            editor.putInt(getString(R.string.alarm_delays_allowed), 0);
            editor.apply();
        }

    }

    private boolean upButton, downButton;

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            downButton = true;
        } else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            upButton = true;
        }
        if(upButton || downButton) {
            Log.i("button down", "yes");
            Objects.requireNonNull(Navigation.findNavController(this, R.id.nav_host_fragment_activity_main).getCurrentBackStackEntry()).getSavedStateHandle().set("volume_down", "1");
        }
        return true;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            downButton = false;
        } else if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            upButton = false;
        }
        Objects.requireNonNull(Navigation.findNavController(this, R.id.nav_host_fragment_activity_main).getCurrentBackStackEntry()).getSavedStateHandle().set("volume_up", "1");
        return true;
    }

}