package com.ayal.todoboom;

import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final int NOT_COMPLETED = 111;
    public static final int COMPLETED = 222;
    private String editTextString;
    private TodoAdapter adapter;
//    private ArrayList<String> todoListStrings;
//    private ArrayList<String> todoListDone;

    private FirebaseFirestore db;
    private static int count = 0;

    public Todo getTodo(int position){
        return adapter.getTodo(position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new TodoAdapter();
        final Button button = findViewById(R.id.btn1);
        final EditText input = findViewById(R.id.edit);
        editTextString = input.getText().toString();

        // restore from fireStore
        refreshDataWithOneTimeQuery();


//        final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);


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
                    // move to not completed
                    Intent intent = new Intent(MainActivity.this, NotCompletedTodo.class);
                    intent.putExtra("todo position", position); //todo maybe delete
                    intent.putExtra("todo message2", todo.getDescription());
                    intent.putExtra("creation", todo.creation_timestamp);
                    intent.putExtra("edit", todo.edit_timestamp);
                    Log.d("creation timestamp", todo.creation_timestamp +"hey");
                    startActivityForResult(intent, NOT_COMPLETED);

                } else {
                    // if completed_todo is pressed
                    Intent intent = new Intent(MainActivity.this, CompletedTodo.class);
                    intent.putExtra("todo position", position); //todo maybe delete
                    intent.putExtra("todo message", todo.getDescription());
                    startActivityForResult(intent, COMPLETED);

//                    String todoText = ttodo.getDescription();
                }
            }
        });

        // try adding todoitem
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
                    // add todoboom object
                    count+=1;
                    String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                    String id =String.valueOf(count);
                    Todo todo = new Todo(description,timestamp,timestamp, false);
                    adapter.addTodoItem(todo);

                    // add to firebase todo creation timestamp
//                    db = FirebaseFirestore.getInstance();
                    db.collection("todoList").document(todo.id).set(todo);
                    input.getText().clear();
                }
            }
        });
    }

    //todo check
//    @Override
//    protected void onResume() {
//        super.onResume();
////        refreshDataWithOneTimeQuery();
//    }

    //todo delete
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("editText", editTextString);
    }

    public void SnackBarError(View view, String message, int duration) {
        Snackbar.make(view, message, duration).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // if returned from a completed todo_item
        if (requestCode == COMPLETED) {
            if (resultCode == RESULT_OK) {
                int position = data.getIntExtra("todo position", -1);
                boolean toDelete = data.getBooleanExtra("delete", false);
                boolean unmarkDone = data.getBooleanExtra("unmark", false);

                Todo todo = adapter.getTodo(position);
                // check if marked undone
                if (unmarkDone) {
                    todo.setDone(false);
                    String undone = todo.getDescription().split(" is done")[0];
                    todo.setDescription(undone);
                    String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                    todo.setEdit_timestamp(timestamp);                    // update fireStore
                    db = FirebaseFirestore.getInstance();
                    db.collection("todoList").document(todo.id).set(todo);
                }
                // check if deleted
                if (toDelete) {
                    adapter.deleteTodoItem(todo);
                    db = FirebaseFirestore.getInstance();
                    Log.d("creation timestamp",todo.creation_timestamp);
                    db.collection("todoList").document(todo.id).delete();

                }
                adapter.notifyDataSetChanged();
            }
        }
        // if returned from a not completed todo_item
        if (requestCode == NOT_COMPLETED) {
            if (resultCode == RESULT_OK) {
                int position = data.getIntExtra("todo position", -1);
                boolean markDone = data.getBooleanExtra("markDone", false);
                String updated_message = data.getStringExtra("changed message");

                Todo todo = adapter.getTodo(position);
                // if updated message
                if (updated_message != null) {
                    todo.setDescription(updated_message);
                    String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                    todo.setEdit_timestamp(timestamp);
                    //update in fireStore
                    db = FirebaseFirestore.getInstance();
                    db.collection("todoList").document(todo.id).set(todo);
                    adapter.notifyDataSetChanged();
                    View view = findViewById(R.id.main_layout_id);
                    String message = "TODO was changed successfully";
                    int duration = Snackbar.LENGTH_SHORT;
                    Snackbar.make(view, message, duration).show();
//                    todo.setDescription(AppManager.editText); todo check
                }
                // if marked as done
                if (markDone) {
                    todo.setDone(true);
                    todo.setDescription(todo.description + " is done");
                    String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                    todo.setEdit_timestamp(timestamp);
                }

                //update fireStore
                db = FirebaseFirestore.getInstance();
                db.collection("todoList").document(todo.id).set(todo);
//                AppManager.updateGson(getApplicationContext(), adapter.getTodoList(), editTextString);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void refreshDataWithOneTimeQuery() {
        db = FirebaseFirestore.getInstance();


        db.collection("todoList").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                QuerySnapshot result = task.getResult();
                if (task.isSuccessful() && result != null) {
                    adapter.clearTodoList(); //todo check if need array list in main activity
                    for (DocumentSnapshot document : result) {
//                        String docId = document.getId(); //todo check if needed
                        Todo todo = document.toObject(Todo.class);
//                        todo.id = document.getId();
                        adapter.addTodoItem(todo);
                        adapter.notifyDataSetChanged();
//                       allTodos.put(docId, item); todo check order
                    }
                } else {
                    Log.e("error", "Error getting documents.", task.getException());
                }
            }
        });
    }

}

