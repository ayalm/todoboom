package com.ayal.todoboom;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class AppManager extends Application {

    public static ArrayList<Todo> todoArrayList;
    public static String editText;

    @Override
    public void onCreate() {
        super.onCreate();

        // get the todoList from the gson
        SharedPreferences sp = this.getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = sp.getString("list", null);
        todoArrayList = gson.fromJson(json, new TypeToken<ArrayList<Todo>>() {
        }.getType());

        //todo check if needed
        String json2 = sp.getString("text", null);
        editText = gson.fromJson(json2, String.class);


//        if (todoArrayList == null) { //todo check
//            todoArrayList = new ArrayList<>();
//        }
        editor.apply();

        // log the length of the todoList
        Log.d(getString(R.string.list_size), Integer.toString(todoArrayList.size()));

    }

    public static void updateGson(Context context, ArrayList<Todo> list, String text) {
        todoArrayList = list;
        SharedPreferences sp = context.getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString("list", json);
        editor.putString("text", text);
        editor.apply();
    }
}
