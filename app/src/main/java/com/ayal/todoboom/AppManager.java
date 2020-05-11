package com.ayal.todoboom;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class Manager extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences sp = this.getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();

        String json = sp.getString("list", null);
        ArrayList<Todo> todoArrayList = gson.fromJson(json, new TypeToken<ArrayList<Todo>>() {
        }.getType());

        if (todoArrayList == null) { //todo check
            todoArrayList = new ArrayList<>();
        }
        editor.apply();
        Log.d(getString(R.string.list_size), Integer.toString(todoArrayList.size()));


    }

    public void updateGson(ArrayList<Todo> list) {
        //todo check this/context
        SharedPreferences sp = this.getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("list", json);
        editor.apply();
    }
}
