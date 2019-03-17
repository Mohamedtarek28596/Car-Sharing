package com.example6767gh.mytestauthentication;

import android.support.annotation.NonNull;
import android.support.design.animation.Positioning;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    ArrayList<String>  mContact;
    ArrayList<String>  mContact2;
    ArrayList<String>  mContact3;

    public MainAdapter(ArrayList<String> contact,ArrayList<String> contact2,ArrayList<String> contact3) {
        mContact =contact;
        mContact2=contact2;
        mContact3=contact3;
    }


    @Override
    public MainAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MainAdapter.ViewHolder viewHolder, int position) {
        viewHolder.mStart.setText(mContact.get(position));
        viewHolder.mEnd.setText(mContact2.get(position));
        viewHolder.mTime.setText(mContact3.get(position));


    }

    @Override
    public int getItemCount() {
        return mContact.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mStart;
        public TextView mEnd;
        public TextView mTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mStart = itemView.findViewById(R.id.start);
            mEnd = itemView.findViewById(R.id.end);
            mTime = itemView.findViewById(R.id.time);

        }
    }
}
