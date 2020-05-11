package com.ayal.todoboom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String editTextString;
    private TodoAdapter adapter;
    private ArrayList<String> todoListStrings;
    private ArrayList<String> todoListDone;


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new TodoAdapter();
        final Button button = findViewById(R.id.btn1);
        final EditText input = findViewById(R.id.edit);
        editTextString = input.getText().toString();

        // restore state
        if (savedInstanceState != null) {
            todoListStrings = savedInstanceState.getStringArrayList("stringArray");
            todoListDone = savedInstanceState.getStringArrayList("doneArray");
            editTextString = savedInstanceState.getString("editText");

            input.setText(editTextString);
            for (int i = 0; i < todoListStrings.size(); i++) {
                Todo todoItem = new Todo(todoListStrings.get(i), Boolean.parseBoolean(todoListDone.get(i)));
                adapter.addTodoItem(todoItem);

            }
        }
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);


        //set recyclerview
        RecyclerView recyclerView = findViewById(R.id.todoRecycler);
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter.setTodoClickListener(new TodoClickListener() {
            @Override
            public void onTodoClicked(int position) {
                Todo todo = adapter.getTodo(position);
                //when TodoItem clicked
                if (!todo.isDone()) {
                    String todoText = todo.getDescription();

                    // change values of todoItem
                    todo.setDone(true);
                    todo.setDescription(todoText + " is done");
                    adapter.notifyDataSetChanged();

                    //add snackBar message
                    View view = findViewById(R.id.main_layout_id);
                    String message = "TODO " + todoText + " is now DONE. BOOM!";
                    int duration = Snackbar.LENGTH_SHORT;
                    SnackBarError(view, message, duration);
                }
            }
        });

        adapter.setLongTodoClickListener(new TodoLongClickListener() {
            @Override
            public void onTodoLongClicked(int position) {
                builder1.setMessage("Are You Sure to delete?");
                builder1.setCancelable(true);
                final Todo todo_delete = adapter.getTodo(position);

                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        adapter.deleteTodoItem(todo_delete);
                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });

                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert11 = builder1.create();
                alert11.show();


                Todo todo = adapter.getTodo(position);
                //when TodoItem clicked
                if (!todo.isDone()) {
                    String todoText = todo.getDescription();

                    // change values of todoItem
                    todo.setDone(true);
                    todo.setDescription(todoText + " is done");
                    adapter.notifyDataSetChanged();

                    //add snackBar message
                    View view = findViewById(R.id.main_layout_id);
                    String message = "TODO " + todoText + " is now DONE. BOOM!";
                    int duration = Snackbar.LENGTH_SHORT;
                    SnackBarError(view, message, duration);
                }
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = input.getText().toString();
                // put an error if empty
                if (description.isEmpty()) {
                    View view = findViewById(R.id.main_layout_id);
                    String message = "you can't create an empty TODO item, oh silly!";
                    int duration = Snackbar.LENGTH_SHORT;
                    SnackBarError(view, message, duration);
                } else {
                    // create todoboom object
                    Todo todo = new Todo(description, false);
                    adapter.addTodoItem(todo);
                    input.getText().clear();
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        todoListStrings = new ArrayList<>();
        todoListDone = new ArrayList<>();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            todoListStrings.add(adapter.getTodo(i).getDescription());
            todoListDone.add(adapter.getTodo(i).isDone.toString());
        }
        outState.putStringArrayList("stringArray", todoListStrings);
        outState.putStringArrayList("doneArray", todoListDone);
        outState.putString("editText", editTextString);
    }

    public void SnackBarError(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }
}

