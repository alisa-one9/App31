package com.example.app31.interfaces;

import com.example.app31.models.Note;

public interface OnItemClickListener {
    void onClick(Note note, int position);

    void  onLongClick(Note note,int position);
}
