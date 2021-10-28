package com.example.tugasakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddNotes extends AppCompatActivity {
    EditText title, content;
    FloatingActionButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        title = findViewById(R.id.titleInput);
        content = findViewById(R.id.contentInput);

        addButton = findViewById(R.id.saveButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseTool myDB = new DatabaseTool(AddNotes.this);
                myDB.addNotes(title.getText().toString().trim(), content.getText().toString().trim());
                finish();
            }
        });
    }
}