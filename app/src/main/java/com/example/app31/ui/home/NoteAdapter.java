package com.example.app31.ui.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app31.App;
import com.example.app31.R;
import com.example.app31.interfaces.OnItemClickListener;
import com.example.app31.models.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<Note>list = new ArrayList<>();
    private int position;
    private OnItemClickListener onItemClickListener;
    private Button btn_menu_sort;






    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_note, parent, false);
        return new ViewHolder(view);

    }
    public NoteAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.ViewHolder holder, int position) {
          if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.YELLOW);
        } else {
            holder.itemView.setBackgroundColor(Color.MAGENTA);
        }
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }


    public void addItem(Note note) {
        list.add(0, note);
        notifyItemChanged(list.indexOf(0));
    }
    public void  setList(ArrayList<Note>list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
            }
    public  void sortList(ArrayList<Note>list) {
        this.list.clear();
        App.getAppDatabase().taskDao().sortAll();
        notifyDataSetChanged();
    }
    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addApdateList(List<Note> notes) {
        list.clear();
        list = notes;
        notifyDataSetChanged();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, createddAt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textTitle);
            createddAt = itemView.findViewById(R.id.textT);
            itemView.setOnClickListener(v ->
                    onItemClickListener.onClick(list.get(getAdapterPosition()),getAdapterPosition()));

            itemView.setOnLongClickListener(v1 -> {
                AlertDialog alertDialog = new AlertDialog.Builder(itemView.getContext()).setMessage("Вы хотите удалить")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                              App.getAppDatabase().taskDao().delete(list.get(getAdapterPosition()));
                              remove(getAdapterPosition());
                            }
                        }).setNegativeButton("НЕТ",null).create();
                alertDialog.show();
                return true;
            });

        }


        public void bind(Note note) {

            name.setText(note.getName());
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm, dd MM yyyy");
            String date = dateFormat.format(System.currentTimeMillis());
            createddAt.setText(date);
        }

    }


}






