package com.example.lakshya.refresh;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    listArrayAdapter listAdapter;
    ArrayList<ListItem> itemArrayList;
    final static  int NEW_LIST = 1;
    PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        itemArrayList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.expenseListView);
        listAdapter = new listArrayAdapter(this, itemArrayList);
        listView.setAdapter(listAdapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, ItemDetailsActiity.class);
                startActivityForResult(i,NEW_LIST);
                Toast.makeText(MainActivity.this,"Enter Details Here",Toast.LENGTH_SHORT).show();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Intent i = new Intent(MainActivity.this, ItemDetailsActiity.class);
                i.putExtra("id", itemArrayList.get(position).id);
                i.putExtra("title", itemArrayList.get(position).title);
                i.putExtra("type", itemArrayList.get(position).type);
                i.putExtra("date", itemArrayList.get(position).dueDate);
                i.putExtra("time",itemArrayList.get(position).dueTime);
                i.putExtra("requestCode", 1);
                startActivityForResult(i, 1);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final int id1 = itemArrayList.get(position).id;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete");
                builder.setCancelable(false);
                View v = getLayoutInflater().inflate(R.layout.dialog_view2, null);
                final TextView tv = (TextView) v.findViewById(R.id.dialogTextView);
                tv.setText("Are you sure you want to delete ??");
                builder.setView(v);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemArrayList.remove(position);
                        ListOpenHelper expenseOpenHelper = new ListOpenHelper(MainActivity.this);
                        SQLiteDatabase database = expenseOpenHelper.getWritableDatabase();
                        database.delete(ListOpenHelper.LIST_TABLE_NAME, ListOpenHelper.LIST_ID + "=" + id1, null);
                        listAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });

        updateItemArrayList();
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.MONTH, 5);
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.DAY_OF_MONTH,7);

        calendar.set(Calendar.HOUR_OF_DAY, 5);
        calendar.set(Calendar.MINUTE, 43);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.AM_PM,Calendar.PM);

        Intent myIntent = new Intent(MainActivity.this, MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }
    private void updateItemArrayList() {
        ListOpenHelper listOpenHelper = ListOpenHelper.getOpenHelperInstance(this);
        itemArrayList.clear();
        SQLiteDatabase database = listOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(ListOpenHelper.LIST_TABLE_NAME,null,null,null,null, null, null);
        while(cursor.moveToNext()){

            String title = cursor.getString(cursor.getColumnIndex(ListOpenHelper.LIST_TITLE));
            String date = cursor.getString(cursor.getColumnIndex(ListOpenHelper.LIST_DATE));
            String time = cursor.getString(cursor.getColumnIndex(ListOpenHelper.LIST_TIME));
            int id = cursor.getInt(cursor.getColumnIndex(ListOpenHelper.LIST_ID));
            String type = cursor.getString(cursor.getColumnIndex(ListOpenHelper.LIST_TYPE));
            ListItem e = new ListItem(id, title,type,date,time);
            itemArrayList.add(e);
        }

        listAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            if (resultCode == RESULT_OK) {
                updateItemArrayList();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }if(id == R.id.aboutUs){
            Intent i = new Intent();
            i.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.parse("https://www.codingninjas.in");
            i.setData(uri);

            startActivity(i);
        }else if(id == R.id.contactUs){
            Intent i = new Intent();
            i.setAction(Intent.ACTION_CALL);
            Uri uri = Uri.parse("tel:123345");
            i.setData(uri);
            startActivity(i);
        }else if(id == R.id.feedback){
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SENDTO);
            Uri uri = Uri.parse("mailto:manisha@codingninjas.in");
            i.putExtra(Intent.EXTRA_SUBJECT,"Feedback");
            i.setData(uri);
            if(i.resolveActivity(getPackageManager()) != null) {
                startActivity(i);
            }
        }


        return super.onOptionsItemSelected(item);
    }

}
