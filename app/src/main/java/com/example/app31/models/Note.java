package com.example.app31.models;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
    private long id;
    private String name;
    private Date createdAt;

    public Note(String name,Date createdAt) {
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
