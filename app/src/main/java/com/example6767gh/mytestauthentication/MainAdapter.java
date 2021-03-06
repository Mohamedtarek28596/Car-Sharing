package com.example6767gh.mytestauthentication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.animation.Positioning;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    ArrayList<Trips>  trips;
    ArrayList<Cars>  car1;
    Context context;

    public MainAdapter(ArrayList<Trips> trips, ArrayList<Cars> car, Context context) {
        this.trips = trips;
        this.car1=car;
        this.context = context;
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
        viewHolder.mType.setText(car1.get(position).getType());
        viewHolder.mNumber.setText(car1.get(position).getNumber());
        viewHolder.mColor.setText(car1.get(position).getColor());
        Picasso.with(context).load(car1.get(position).getImage())
                .into(viewHolder.mImage);

    }

    @Override
    public int getItemCount() {
        return trips.size() ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mStart;
        public TextView mEnd;
        public TextView mTime;
        public TextView mType;
        public TextView mColor;
        public TextView mNumber;
        public CircleImageView mImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mStart = itemView.findViewById(R.id.start);
            mEnd = itemView.findViewById(R.id.end);
            mTime = itemView.findViewById(R.id.time);
            mType = itemView.findViewById(R.id.type);
            mNumber = itemView.findViewById(R.id.number);
            mColor = itemView.findViewById(R.id.color);
            mImage = itemView.findViewById(R.id.ivProfilePic);
        }
    }
}
