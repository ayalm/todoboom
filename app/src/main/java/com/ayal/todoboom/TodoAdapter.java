package com.ayal.todoboom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoHolder> {

    private ArrayList<Todo> todoList = new ArrayList<>();
    private TodoClickListener todoClickListener;


    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_one_todo, parent, false);

        // Return a new holder instance
        return new TodoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, final int position) {
        // Get the data model based on position
        final Todo todo = todoList.get(position);
        holder.myTextView.setText(todo.description);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (todoClickListener != null) {
                    todoClickListener.onTodoClicked(position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public TodoClickListener getTodoClickListener() {
        return todoClickListener;
    }

    public void setTodoClickListener(TodoClickListener todoClickListener) {
        this.todoClickListener = todoClickListener;
    }

    public void addTodoItem(Todo todo) {
        todoList.add(todo);
    }

    public Todo getTodo(int index) {
        return todoList.get(index);
    }

    public void setTodo(Todo todo, int position) {
        this.todoList.set(position, todo);
    }
}


