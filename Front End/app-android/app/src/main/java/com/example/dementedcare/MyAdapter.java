package com.example.dementedcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    // declare two variables
    Context context;
    ArrayList<data> userArrayList;
    private OnItemClickListener itemClickListener;

    public MyAdapter(Context context, ArrayList<data> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    // onclick listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        data data = userArrayList.get(position);

        if (data != null) {
            holder.email.setText(data.getEmail());

            holder.itemView.setOnClickListener(view -> {
                if (itemClickListener != null) {
                    // Pass userId to onItemClick
                    itemClickListener.onItemClick(data.getEmail());
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(data data);

        void onItemClick(String email);


    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView email;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.email);
        }
    }
}
