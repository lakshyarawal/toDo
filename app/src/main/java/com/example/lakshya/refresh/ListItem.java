package com.example.lakshya.refresh;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by LAKSHYA on 6/24/2017.
 */
@Entity(tableName = "notes")
public class ListItem implements Serializable{
    @PrimaryKey(autoGenerate = true)
    int id;

    String title;
    String type;
    String dueDate;
    String dueTime;

    public ListItem(String title, String type, String dueDate, String dueTime) {
        this.title = title;
        this.type = type;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }
}