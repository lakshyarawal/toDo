package com.example.lakshya.refresh;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LAKSHYA on 6/29/2017.
 */

public class ListOpenHelper extends SQLiteOpenHelper {
    public final static String LIST_TABLE_NAME  = "Expense";
    public final static String LIST_TITLE  = "title";
    public final static String LIST_ID  = "_id";
    public final static String LIST_DATE = "dueDate" ;
    public final static String LIST_TIME = "dueTime" ;
    public final static String LIST_TYPE = "type" ;
    public  static ListOpenHelper listOpenHelper;


    public static ListOpenHelper getOpenHelperInstance(Context context){

        if(listOpenHelper == null){
            listOpenHelper = new ListOpenHelper(context);
        }
        return listOpenHelper;

    }


    public ListOpenHelper(Context context) {
        super(context, "List.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table " + LIST_TABLE_NAME +" ( " + LIST_ID +
                " integer primary key autoincrement, " + LIST_TITLE +" text, "
                + LIST_DATE + " text, "+ LIST_TIME + " text, "
                + LIST_TYPE + " text);";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

