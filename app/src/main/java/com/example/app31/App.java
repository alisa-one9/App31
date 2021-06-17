package com.example.app31;

import android.app.Application;

import androidx.room.Room;

import com.example.app31.room.AppDatabase;
import com.google.firebase.FirebaseApp;

public class App extends Application {
    private static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        appDatabase = Room
                .databaseBuilder(this, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    public static AppDatabase getAppDatabase() {
        return appDatabase;
    }

}
