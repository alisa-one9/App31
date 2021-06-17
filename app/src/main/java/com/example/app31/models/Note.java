package com.example.app31.models;

import android.provider.ContactsContract;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.app31.DateConverter;
import com.google.common.base.Converter;

import java.io.Serializable;
import java.util.Date;

@Entity
@TypeConverters(DateConverter.class)
public class Note implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String docId;
    private String name;
    private Date createdAt;

    public Note(String title) {
        this.name = title;
    }

    public Note(String name, Date createdAt) {
        this.name = name;
        this.createdAt = createdAt;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
