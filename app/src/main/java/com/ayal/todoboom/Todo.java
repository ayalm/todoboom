package com.ayal.todoboom;


import android.annotation.SuppressLint;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Todo {

    String description; //todo content
    String creation_timestamp;
    String edit_timestamp;
    int id;
    Boolean isDone;
    String dbId;

    public Todo(){

    }

    @SuppressLint("SimpleDateFormat")
    public Todo(String description, boolean isDone, int id) {
        this.description = description;
        this.isDone = isDone;
        this.creation_timestamp =  new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        Log.d("check creation", this.creation_timestamp);
        this.edit_timestamp = this.creation_timestamp;
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

    public void setEdit_timestamp(String edit_timestamp) {
        this.edit_timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
    }

    //todo getteres and setters


}
