package com.ayal.todoboom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.btn1);
        final EditText input = findViewById(R.id.edit);
        final TextView textView = findViewById(R.id.text);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = input.getText().toString();
                textView.setText(string);
                input.getText().clear();
            }
        });
    }
}

