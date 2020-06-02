package com.ayal.todoboom;


import android.annotation.SuppressLint;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Todo {

    private static long count = 0;

    String description;
    String creation_timestamp;
    String edit_timestamp;
    String id;
    Boolean isDone;
//    String dbId;

    public Todo() {
    }

    @SuppressLint("SimpleDateFormat")
    public Todo(String description, String creation_timestamp, String edit_timestamp, boolean isDone) {
        this.id = String.valueOf(count);
        count += 1;
        this.description = description;
        this.isDone = isDone;
        this.creation_timestamp = creation_timestamp;
        Log.d("check creation", this.creation_timestamp);
        this.edit_timestamp = edit_timestamp;
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return this.isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public String getCreation_timestamp() {
        return creation_timestamp;
    }

    public void setCreation_timestamp(String creation_timestamp) {
        this.creation_timestamp = creation_timestamp;
    }

    public void setEdit_timestamp(String edit_timestamp) {
        this.edit_timestamp = edit_timestamp;
    }



    public String getEdit_timestamp() {
        return edit_timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
