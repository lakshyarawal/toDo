package com.example.lakshya.refresh;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class ItemDetailsActiity extends AppCompatActivity {
    String title, category, date,time;
    long dateTime;
    int position, id, requestCode;
    EditText titleTextView, categoryTextView, dateTextView,timeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details_actiity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                String newTitle = titleTextView.getText().toString();
                String newCategory = categoryTextView.getText().toString();
                String newDate = dateTextView.getText().toString();
                String newTime = timeTextView.getText().toString();
                Intent i = new Intent();
                i.putExtra("title", newTitle);
                i.putExtra("position", position);
                ListOpenHelper listOpenHelper = ListOpenHelper.getOpenHelperInstance(ItemDetailsActiity.this);

                SQLiteDatabase database = listOpenHelper.getWritableDatabase();

                ContentValues cv = new ContentValues();
                cv.put(ListOpenHelper.LIST_TITLE, newTitle);
                cv.put(ListOpenHelper.LIST_TYPE, newCategory);
                cv.put(ListOpenHelper.LIST_DATE, newDate);
                cv.put(ListOpenHelper.LIST_TIME,newTime);

                if (id == -1) {
                    database.insert(ListOpenHelper.LIST_TABLE_NAME, null, cv);
                } else {
                    database.update(ListOpenHelper.LIST_TABLE_NAME, cv, ListOpenHelper.LIST_ID + " =?", new String[]{String.valueOf(id)});
                }

                Intent i1 = new Intent();
                i1.putExtra("title", newTitle);
                setResult(RESULT_OK, i);
                finish();


            }
        });
        titleTextView = (EditText) findViewById(R.id.editText);
        categoryTextView = (EditText) findViewById(R.id.itemDetailTypeTextView);
        dateTextView = (EditText) findViewById(R.id.itemDetailDateTextView);
        timeTextView = (EditText) findViewById(R.id.itemDetailTimeTextView);
        Intent i = getIntent();
        title = i.getStringExtra("title");
        category = i.getStringExtra("type");
        date = i.getStringExtra("date");
        time = i.getStringExtra("time");
        position = i.getIntExtra("position", 0);
        id = i.getIntExtra("id", -1);
        requestCode = i.getIntExtra("requestCode", 0);
        titleTextView.setText(title);
        categoryTextView.setText(category);
        dateTextView.setText(date);
        timeTextView.setText(time);
        // Steps for Date picker
        // Will show Date picker dialog on clicking edit text

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                int month = newCalendar.get(Calendar.MONTH);  // Current month
                int year = newCalendar.get(Calendar.YEAR);   // Current year
                showDatePicker(ItemDetailsActiity.this, year, month, 1);
            }
        });
        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(ItemDetailsActiity.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                timeTextView.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });
    }

    public void showDatePicker(Context context, int initialYear, int initialMonth, int initialDay) {

        // Creating datePicker dialog object
        // It requires context and listener that is used when a date is selected by the user.

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    //This method is called when the user has finished selecting a date.
                    // Arguments passed are selected year, month and day
                    @Override
                    public void onDateSet(DatePicker datepicker, int year, int month, int day) {

                        // To get epoch, You can store this date(in epoch) in database
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);
                        dateTime = calendar.getTime().getTime();
                        // Setting date selected in the edit text
                        dateTextView.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, initialYear, initialMonth, initialDay);

        //Call show() to simply show the dialog
        datePickerDialog.show();

    }



    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}
