package com.example.lakshya.refresh;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    RecyclerAdapter mAdapter;
    ArrayList<ListItem> itemArrayList = new ArrayList<>();
    private static final int REQUEST_ADD = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //toolbar.setNavigationIcon(R.drawable.ic_toolbar);
        toolbar.setTitle("");
        toolbar.setSubtitle("");
        //toolbar.setLogo(R.drawable.ic_toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent add = new Intent(MainActivity.this,ItemDetailsActiity.class);
                startActivityForResult(add,REQUEST_ADD);
                Toast.makeText(MainActivity.this,"Enter Details Here",Toast.LENGTH_SHORT).show();
            }
        });

        NoteDatabase db = NoteDatabase.getInstance(this);
        final NoteDao noteDao = db.noteDao();
        new AsyncTask<Void,Void,List<ListItem>>(){

            @Override
            protected List<ListItem> doInBackground(Void... voids) {
                return noteDao.getAllNotes();
            }

            @Override
            protected void onPostExecute(List<ListItem> notes) {
                itemArrayList.clear();
                itemArrayList.addAll(notes);
                mAdapter.notifyDataSetChanged();
            }
        }.execute();

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        mAdapter = new RecyclerAdapter(this, itemArrayList, new RecyclerAdapter.NotesClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ListItem note = itemArrayList.get(position);
                Snackbar.make(mRecyclerView,note.getTitle(),Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onRemoveClicked(int position) {
                itemArrayList.remove(position);
                mAdapter.notifyItemRemoved(position);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                Collections.swap(itemArrayList,from,to);
                mAdapter.notifyItemMoved(from,to);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                itemArrayList.remove(position);
                mAdapter.notifyItemRemoved(position);
            }
        });
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ADD && resultCode == RESULT_OK){
            String title = data.getStringExtra("title");
            String type = data.getStringExtra("type");
            String date = data.getStringExtra("date");
            String time = data.getStringExtra("time");
            ListItem note = new ListItem(title,type,date,time);
            int size = itemArrayList.size();
            itemArrayList.add(note);
            mAdapter.notifyItemInserted(size);
        }
    }
}
