package com.mtcbustracking.bustracking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Custom_Bus_Capacity extends RecyclerView.Adapter<Custom_Bus_Capacity.MyViewHolder> {

    ArrayList placelist, occupiedlist, availablelist;
    Context context;
    public Custom_Bus_Capacity(Context context, ArrayList placelist, ArrayList occupiedlist, ArrayList availablelist) {
        this.context = context;
        this.placelist = placelist;
        this.occupiedlist = occupiedlist;
        this.availablelist = availablelist;
    }

    @NonNull
    @Override
    public Custom_Bus_Capacity.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_view_bus_capacity, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull Custom_Bus_Capacity.MyViewHolder holder, int position) {
        holder.place.setText(placelist.get(position).toString());
        holder.available.setText(availablelist.get(position).toString());
        holder.occupied.setText(occupiedlist.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return placelist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView place, occupied, available;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            place = itemView.findViewById(R.id.place);
            occupied = itemView.findViewById(R.id.occupied);
            available = itemView.findViewById(R.id.available);
        }
    }
}
