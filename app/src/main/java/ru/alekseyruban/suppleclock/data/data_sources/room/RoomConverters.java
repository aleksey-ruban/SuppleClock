package ru.alekseyruban.suppleclock.data.data_sources.room;

import androidx.room.TypeConverter;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RoomConverters {
    @TypeConverter
    public static ArrayList<String> fromStringToArrayList(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayListToString(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }


    @TypeConverter
    public static ArrayList<Boolean> fromStringToArrayListBool(String value) {
        Type listType = new TypeToken<ArrayList<Boolean>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayListBoolToString(ArrayList<Boolean> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

}
