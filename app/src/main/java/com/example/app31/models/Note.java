package com.example.app31.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
@Entity
public class Note implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private long createdAt;

    public Note(String name,long createdAt) {
        this.name = name;
        this.createdAt=createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
