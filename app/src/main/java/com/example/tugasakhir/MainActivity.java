package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addButton;

    DatabaseTool myDB;
    ArrayList<String> note_id, note_title, note_content;
    CustomAdapter customAdapter;

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = MainActivity.this;
        recyclerView = findViewById(R.id.notesContent);
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNotes.class);
                activity.startActivityForResult(intent, 1);
            }
        });

        myDB = new DatabaseTool(MainActivity.this);
        note_id = new ArrayList<>();
        note_title = new ArrayList<>();
        note_content = new ArrayList<>();

        storeNoteData();
        customAdapter = new CustomAdapter(MainActivity.this, this, note_id, note_title, note_content);
        recyclerView.setAdapter(customAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(swipeaction);
        helper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate();
        }

    }

    ItemTouchHelper.SimpleCallback swipeaction = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT){

        @Override
        public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
            int pos = viewHolder.getAdapterPosition();
            confirmDialog(note_title.get(pos), note_id.get(pos));
        }
    };

    void confirmDialog(String title, String id){
        AlertDialog.Builder builder  = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Delete " + title + " ?");
        builder.setMessage("Are you sure you want to delete " + title + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseTool db = new DatabaseTool(MainActivity.this);
                db.deleteOneNote(id);
                removeData();
                storeNoteData();
                customAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                customAdapter.notifyDataSetChanged();
            }
        });
        builder.create().show();
    }

    void removeData(){
        note_id.removeAll(note_id);
        note_title.removeAll(note_title);
        note_content.removeAll(note_content);
    }

    void storeNoteData(){
        Cursor cursor = myDB.readData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                note_id.add(cursor.getString(0));
                note_title.add(cursor.getString(1));
                note_content.add(cursor.getString(2));
            }
        }
    }


}