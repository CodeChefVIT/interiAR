package com.example.interiordesign;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ImageViewHolder> {
    private int[] images;
    private Context context;

    public RecyclerAdapter(int[] images,Context context) {
        this.images = images;
        this.context=context;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout, parent, false);
        ImageViewHolder viewHolder = new ImageViewHolder(view,context,images);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        int imageid = images[position];
        holder.furniture.setImageResource(imageid);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView furniture;
        Context context;
        int[] images;

        public ImageViewHolder(View view,Context context,int[] images) {
            super(view);
            furniture = view.findViewById(R.id.furniture);
            view.setOnClickListener(this);
            this.context=context;
            this.images=images;
        }

        @Override
        public void onClick(View v) {
            int pos=getAdapterPosition();
            if (pos==0){
                Intent intent=new Intent(context,MainActivity3.class);
                context.startActivity(intent);
            }
        }
    }
}
