package com.codechefvit.interiAR;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ImageViewHolder> {
    private int[] images;
    private String[] names;
    private Context context;

    public RecyclerAdapter(int[] images,String[] names,Context context) {
        this.images = images;
        this.names=names;
        this.context=context;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout, parent, false);
        ImageViewHolder viewHolder = new ImageViewHolder(view,context,images,names);
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
        Button button;
        Context context;
        int[] images;
        String[] names;
        public ImageViewHolder(View view,Context context,int[] images,String[] names) {
            super(view);
            furniture = view.findViewById(R.id.furniture);
            button=view.findViewById(R.id.viewbtn);
            button.setOnClickListener(this);
            this.context=context;
            this.images=images;
            this.names=names;
        }

        @Override
        public void onClick(View v) {
            int pos=getAdapterPosition();
            Intent intent=new Intent(context,MainActivity3.class);
            intent.putExtra("selected_item",names[pos]);
            context.startActivity(intent);
        }
    }
}
