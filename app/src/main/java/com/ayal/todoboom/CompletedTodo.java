package com.ayal.todoboom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CompletedTodo extends AppCompatActivity {

    private int position;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed_todo);

        Intent intentCreatedMe = getIntent();
        position = intentCreatedMe.getIntExtra("todo position", -1);

        // todo get todo from position - change to firebase
//        Todo todo = AppManager.todoArrayList.get(position);
//        Todo todo = M
        message = intentCreatedMe.getStringExtra("todo message");

        //show todo_message
        TextView todo_message = findViewById(R.id.completed_todo);
        todo_message.setText(message);

        //unmark button
        Button unmark_todo_button = findViewById(R.id.btn_unmark);
        unmark_todo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unmarkTodo();
            }

        });

        final AlertDialog.Builder builder1 = new AlertDialog.Builder(this);


        Button delete_todo_button = findViewById(R.id.btn_delete);
        delete_todo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder1.setMessage("Are You Sure to delete?");
                builder1.setCancelable(true);

                // if wants to delete for sure
                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // todo return to main menu and delete todo item
                        Intent intentBack = new Intent(CompletedTodo.this, MainActivity.class);
                        intentBack.putExtra("todo position", position);
                        intentBack.putExtra("delete", true);
                        setResult(RESULT_OK, intentBack);
                        dialog.cancel();
                        finish();
                    }
                });
                // if doesn't want to delete
                builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }
        });
    }

    private void unmarkTodo() {
        // todo go back to main screen and unmark from being done
        Intent intentBack = new Intent(CompletedTodo.this, MainActivity.class);
        intentBack.putExtra("todo position", position);
        intentBack.putExtra("unmark", true);

        setResult(RESULT_OK, intentBack);
        finish();
    }


}
