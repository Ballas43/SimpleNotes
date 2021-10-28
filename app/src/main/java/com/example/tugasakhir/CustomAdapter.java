package com.example.tugasakhir;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.DrawableUtils;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    Activity activity;
    Animation animate;
    private ArrayList note_id, note_title, note_content;

    CustomAdapter(Activity activity, Context context, ArrayList note_id, ArrayList note_title, ArrayList note_content){
        this.activity = activity;
        this.context = context;
        this.note_id = note_id;
        this.note_title = note_title;
        this.note_content = note_content;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_note, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.note_title_txt.setText(String.valueOf(note_title.get(position)));
        holder.note_content_txt.setText(String.valueOf(note_content.get(position)));
        holder.notesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateNotes.class);
                intent.putExtra("id", String.valueOf(note_id.get(position)));
                intent.putExtra("title", String.valueOf(note_title.get(position)));
                intent.putExtra("content", String.valueOf(note_content.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return note_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView note_title_txt, note_content_txt;
        LinearLayout notesLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            note_title_txt = itemView.findViewById(R.id.noteTitle);
            note_content_txt = itemView.findViewById(R.id.noteContent);
            notesLayout = itemView.findViewById(R.id.notesLayout);
            animate = AnimationUtils.loadAnimation(context, R.anim.animation_view);
            notesLayout.setAnimation(animate);
        }
    }
}
