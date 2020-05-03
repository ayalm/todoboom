package com.ayal.todoboom;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TodoHolder extends RecyclerView.ViewHolder {
    TextView myTextView;

    public TodoHolder(View itemView) {
        super(itemView);

        myTextView = itemView.findViewById(R.id.todo_text);
    }
}
