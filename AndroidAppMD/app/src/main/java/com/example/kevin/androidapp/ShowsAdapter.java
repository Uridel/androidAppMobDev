package com.example.kevin.androidapp;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import static com.example.kevin.androidapp.MainActivity.INTENT_DETAIL_REMINDER_TEXT;
import static com.example.kevin.androidapp.MainActivity.INTENT_DETAIL_ROW_NUMBER;
import static com.example.kevin.androidapp.MainActivity.REQUESTCODE;

/**
 * Created by Kevin on 11-4-2017.
 */
public class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.ViewHolder>  {

    private List<Show> mShows;
    private Context mContext;

    private Cursor mShowsCursor;

    public ShowsAdapter(Context mContext, List<Show> cursor) {
        this.mContext = mContext;
        this.mShows = cursor;

    }
    public void updateList(List<Show> newlist) {
        // Set new updated list
        mShows.clear();
        mShows.addAll(newlist);
        notifyDataSetChanged();
    }

    @Override
    public ShowsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(android.R.layout.activity_list_item, null);

        // Return a new holder instance
        ShowsAdapter.ViewHolder viewHolder = new ShowsAdapter.ViewHolder(view);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(final ShowsAdapter.ViewHolder holder, final int position) {



        final Show show =  mShows.get(position);

        holder.textView.setText(show.getShow());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, EditShowActivity.class);
                intent.putExtra(INTENT_DETAIL_ROW_NUMBER, position);
                intent.putExtra(INTENT_DETAIL_REMINDER_TEXT, holder.textView.getText());
                ((MainActivity) mContext).startActivityForResult(intent, REQUESTCODE);
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mShows.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,mShows.size());
                return true;
            }
        });

    }
    @Override
    public int getItemCount() {
        return mShows.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{

        public View mView;
        public TextView textView;

        //Constructor
        public ViewHolder(View v) {


            super(v);
            textView = (TextView) v.findViewById(android.R.id.text1);
            mView = v;
        }
    }
}
