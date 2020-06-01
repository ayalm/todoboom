package com.ayal.todoboom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class NotCompletedTodo extends AppCompatActivity {

    private int position;
    private String message;
    private String creation_timestamp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_completed_todo);

        Intent intentCreatedMe = getIntent();
        position = intentCreatedMe.getIntExtra ("todo position", -1);

        // todo get todo from position - change to firebase
//        final Todo todo = AppManager.todoArrayList.get(position);
        message = intentCreatedMe.getStringExtra ("todo message2");
        creation_timestamp = intentCreatedMe.getStringExtra ("creation");
        String edit_timestamp = intentCreatedMe.getStringExtra ("edit");


        Log.d("todomess",message);

        // option to update todo_message
        final EditText editText = findViewById(R.id.edit_todo);
        editText.setText(message); //todo check


        //apply changes to message
        Button apply_changes_button = findViewById(R.id.btn_apply);
        apply_changes_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show that content was changed successfully
                View view = findViewById(R.id.main_not_completed_todo);
                String message = "TODO was changed successfully";
                int duration = Snackbar.LENGTH_SHORT;
                Snackbar.make(view, message, duration).show();

                // change content of message
//                Intent intentBack = new Intent(NotCompletedTodo.this, MainActivity.class);
//                intentBack.putExtra("todo position",position );
//                intentBack.putExtra("changed message",editedTodoMessage );
//
//                setResult(RESULT_OK, intentBack);
            }
        });

        //show when the todo_item was created and when it was last modified
        TextView created_timestamp = findViewById(R.id.creation_timestamp);
        TextView edited_timestamp = findViewById(R.id.edit_timestamp);

        // todo add timestamp from todo - firebase
//        created_timestamp.setText(todo.creation_timestamp);
//        edited_timestamp.setText(todo.edit_timestamp);

        Log.d("creationcheck",creation_timestamp+"check");

        created_timestamp.setText(creation_timestamp);
        edited_timestamp.setText(edit_timestamp);

        //mark todo_item as done
        Button mark_as_done = findViewById(R.id.btn_mark_done);
        mark_as_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // todo mark todo as done
                // todo go back to main screen
                Intent intentBack = new Intent(NotCompletedTodo.this, MainActivity.class);
                intentBack.putExtra("todo position",position );
                intentBack.putExtra("markDone",true );
//                intentBack.putExtra("changed message",editedTodoMessage ); //todo check if ok here
                final String editedTodoMessage = editText.getText().toString();

//                todo.setDescription(editedTodoMessage);
                setResult(RESULT_OK, intentBack);
                finish();
            }

        });



    }
}
