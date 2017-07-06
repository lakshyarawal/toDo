package com.example.lakshya.refresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by LAKSHYA on 6/24/2017.
 */

public class listArrayAdapter extends ArrayAdapter<ListItem> {
    ArrayList<ListItem> itemArrayList;
    Context context;
    public listArrayAdapter(@NonNull Context context, ArrayList<ListItem> itemArrayList) {
        super(context, 0);
        this.context = context;
        this.itemArrayList = itemArrayList;
    }
    @Override
    public int getCount() {
        return itemArrayList.size();
    }

    static class itemViewHolder{

        TextView nameTextView;
        TextView categoryTextView ;
        TextView dateTextView ;
        TextView timeTextView ;



        itemViewHolder(TextView nameTextView, TextView categoryTextView, TextView dateTextView, TextView timeTextView){
            this.nameTextView = nameTextView;
            this.categoryTextView = categoryTextView;
            this.dateTextView = dateTextView;
            this.timeTextView = timeTextView;

        }

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item,null);
            TextView nameTextView = (TextView) convertView.findViewById(R.id.titleNameTextView);
            TextView categoryTextView = (TextView) convertView.findViewById(R.id.titleCategoryTextView);
            TextView dateTextView = (TextView) convertView.findViewById(R.id.dueDateTextView);
            TextView timeTextView = (TextView) convertView.findViewById(R.id.dueTimeTextView);
            itemViewHolder itemViewHolder = new itemViewHolder(nameTextView, categoryTextView, dateTextView,timeTextView);
            convertView.setTag(itemViewHolder);

        }

        ListItem e = itemArrayList.get(position);
        itemViewHolder expenseViewHolder = (itemViewHolder)convertView.getTag();
        expenseViewHolder.nameTextView.setText(e.title);
        expenseViewHolder.categoryTextView.setText(e.type);
        expenseViewHolder.dateTextView.setText(e.dueDate);
        expenseViewHolder.timeTextView.setText(e.dueTime);



        return  convertView;
    }
}

