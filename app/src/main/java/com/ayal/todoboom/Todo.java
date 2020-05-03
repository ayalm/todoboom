package com.ayal.todoboom;


public class Todo {

    String description;
    Boolean isDone;

    public Todo(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
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
}
