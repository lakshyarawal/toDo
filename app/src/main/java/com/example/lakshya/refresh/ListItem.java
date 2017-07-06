package com.example.lakshya.refresh;

import java.io.Serializable;

/**
 * Created by LAKSHYA on 6/24/2017.
 */

public class ListItem implements Serializable{
    int id;
    String title;
    String type;
    String dueDate;
    String dueTime;

    public ListItem(int id, String title, String type, String dueDate,String dueTime) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.dueDate = dueDate;
        this.dueTime = dueTime;
    }
}