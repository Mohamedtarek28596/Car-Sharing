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
    ArrayList<Trips>  trips;

    public MainAdapter(ArrayList<Trips> trips) {
        this.trips = trips;
    }


    @Override
    public MainAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MainAdapter.ViewHolder viewHolder, int position) {
        viewHolder.mStart.setText(trips.get(position).getStart());
        viewHolder.mEnd.setText(trips.get(position).getEnd());
        viewHolder.mTime.setText(trips.get(position).getTime());


    }

    @Override
    public int getItemCount() {
        return trips.size() ;
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
