package com.example.app31.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.app31.models.Note;

@Database(entities = {Note.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
