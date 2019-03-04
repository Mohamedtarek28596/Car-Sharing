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
    public MainAdapter(ArrayList<String> contact) {
        mContact =contact;
    }


    @Override
    public MainAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MainAdapter.ViewHolder viewHolder, int position) {
        viewHolder.mFullName.setText(mContact.get(position));

    }

    @Override
    public int getItemCount() {
        return mContact.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mFullName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mFullName = itemView.findViewById(R.id.full_name);
        }
    }
}
