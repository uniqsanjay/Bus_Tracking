package com.mtcbustracking.bustracking;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Custom_Rootlist extends RecyclerView.Adapter<Custom_Rootlist.MyViewholder> {

    Context context;
    ArrayList root_no,from, to;

    public Custom_Rootlist(Context context, ArrayList root_no, ArrayList from, ArrayList to) {
        this.context = context;
        this.root_no = root_no;
        this.from = from;
        this.to = to;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_userrootlist, parent, false);
        return new Custom_Rootlist.MyViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, final int position) {
        holder.root.setText(root_no.get(position).toString());
        holder.source.setText(from.get(position).toString());
        holder.dest.setText(to.get(position).toString());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, HomePage.class);
                i.putExtra("Root_no", root_no.get(position).toString());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return root_no.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {

        TextView root, source, dest;
        public MyViewholder(@NonNull View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.cust_rootno);
            source = itemView.findViewById(R.id.from);
            dest = itemView.findViewById(R.id.to);
        }
    }
}
