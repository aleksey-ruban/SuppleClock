package ru.alekseyruban.suppleclock.ui;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import ru.alekseyruban.suppleclock.R;

public class MusicService extends Service {

    MyBinder binder = new MyBinder();
    @SuppressLint("StaticFieldLeak")
    static Context context;

    private MediaPlayer mPlayer;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.htc_basic);
        mPlayer.setLooping(true);
        mPlayer.start();
    }

    public void stop() {
        mPlayer.stop();
        stopSelf();
    }

    public class MyBinder extends Binder {
        public MusicService getServiceSystem()
        {
            return MusicService.this;
        }
    }

}
