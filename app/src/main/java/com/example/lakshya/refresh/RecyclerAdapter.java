package com.example.lakshya.refresh;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.NoteViewHolder> {

    private Context mContext;
    private ArrayList<ListItem> mNotes;
    private NotesClickListener mListener;

    public interface NotesClickListener {
        void onItemClick(View view,int position);
        void onRemoveClicked(int position);
    }


    public RecyclerAdapter(Context context, ArrayList<ListItem> notes,NotesClickListener listener){
        mContext = context;
        mNotes = notes;
        mListener = listener;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);
        return new NoteViewHolder(itemView,mListener);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        ListItem note = mNotes.get(position);
        holder.titleTextView.setText(note.getTitle());
        holder.descTextView.setText(note.getDueDate());
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView titleTextView;
        TextView descTextView;
        Button removeButton;
        NotesClickListener mNotesClickListener;

        public NoteViewHolder(View itemView,NotesClickListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            mNotesClickListener = listener;
            titleTextView = itemView.findViewById(R.id.titleNameTextView);
            descTextView = itemView.findViewById(R.id.titleCategoryTextView);
            removeButton = itemView.findViewById(R.id.remove_button);
            removeButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                if(id == R.id.note_layout){
                    mNotesClickListener.onItemClick(view,position);
                }
                else if(id == R.id.remove_button){
                    mNotesClickListener.onRemoveClicked(position);
                }
            }

        }
    }


}
