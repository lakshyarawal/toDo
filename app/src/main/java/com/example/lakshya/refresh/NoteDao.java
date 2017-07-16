package com.example.lakshya.refresh;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by LAKSHYA on 7/15/2017.
 */
@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes")
    List<ListItem> getAllNotes();


    @Query("SELECT * FROM notes WHERE title = :title AND type = :type")
    List<ListItem> getAllNotesWithName(String title,String type);

    @Insert
    void insertNote(ListItem note);
}
