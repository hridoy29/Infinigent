package com.example.infinigentconsulting;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.List;


public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.MyViewHolder> {

    private final Context mContext;
    private final List<CardElement> cardElements;
    CustomItemClickListener listener;

    public CardViewAdapter(Context mContext, List<CardElement> cardElements, CustomItemClickListener listener) {
        this.mContext = mContext;
        this.cardElements = cardElements;
        this.listener = listener;
    }

    public CardViewAdapter(Context mContext, List<CardElement> cardElements) {
        this.mContext = mContext;
        this.cardElements = cardElements;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_components, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        CardElement album = cardElements.get(position);
        holder.title.setText(album.get_component_name());

        // loading album cover using Glide library
        Glide.with(mContext).load(album.get_component_img()).into(holder.thumbnail);

        //On CLick listener for CardView
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardElements.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            thumbnail = view.findViewById(R.id.thumbnail);
        }
    }
}