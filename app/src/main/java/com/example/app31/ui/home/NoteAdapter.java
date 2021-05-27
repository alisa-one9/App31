package com.example.app31.ui.home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app31.R;
import com.example.app31.interfaces.OnItemClickListener;
import com.example.app31.models.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    ArrayList<Note> list = new ArrayList<>();

    private int position;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public NoteAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_note, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
        this.position = position;
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.YELLOW);
        } else {
            holder.itemView.setBackgroundColor(Color.MAGENTA);
        }
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(Note note) {
        list.add(note);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        list.remove(position);
        notifyItemChanged(position);
    }

    public void getList(int position, Note note) {
        list.set(position, note);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, createddAt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textTitle);
            createddAt = itemView.findViewById(R.id.textT);
            itemView.setOnClickListener(v ->
                    onItemClickListener.onItemClick(name.getText().toString(), getAdapterPosition()));
            itemView.setOnLongClickListener(v1 -> {
                onItemClickListener.onLongClick(getAdapterPosition());
                return true;
            });

        }


        public void bind(Note note) {
            position = getAdapterPosition();
            name.setText(note.getName());
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm, dd MM yyyy");
            String date = dateFormat.format(note.getCreatedAt());
            createddAt.setText(date);
        }

    }


}






