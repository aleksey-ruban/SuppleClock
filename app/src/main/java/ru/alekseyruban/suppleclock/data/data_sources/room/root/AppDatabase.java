package ru.alekseyruban.suppleclock.data.data_sources.room.root;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.alekseyruban.suppleclock.data.data_sources.room.RoomConverters;
import ru.alekseyruban.suppleclock.data.data_sources.room.dao.AlarmCommonDAO;
import ru.alekseyruban.suppleclock.data.data_sources.room.dao.AlarmSimpleDAO;
import ru.alekseyruban.suppleclock.data.data_sources.room.entites.AlarmCommonEntity;
import ru.alekseyruban.suppleclock.data.data_sources.room.entites.AlarmSimpleEntity;

@Database(entities = {AlarmCommonEntity.class, AlarmSimpleEntity.class}, version = 7, exportSchema = false)
@TypeConverters({RoomConverters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract AlarmCommonDAO alarmCommonDAO();

    public abstract AlarmSimpleDAO alarmSimpleDAO();

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
