package com.example.interiordesign;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ImageViewHolder> {
    private int[] images;
    private String[] names;
    private Context context;
    private String cat;

    public RecyclerAdapter(int[] images,String[] names,String cat,Context context) {
        this.images = images;
        this.names=names;
        this.context=context;
        this.cat=cat;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlayout, parent, false);
        ImageViewHolder viewHolder = new ImageViewHolder(view,context,images,names,cat);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        int imageid = images[position];
        holder.furniture.setImageResource(imageid);
        holder.itemname.setText(holder.cat);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView furniture;
        TextView itemname;
        Context context;
        int[] images;
        String[] names;
        String cat;
        public ImageViewHolder(View view,Context context,int[] images,String[] names,String cat) {
            super(view);
            furniture = view.findViewById(R.id.furniture);
            int pos=getAdapterPosition()+1;
            itemname=view.findViewById(R.id.itemname);
            this.cat= String.format("%s%s", cat, pos);
            view.setOnClickListener(this);
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
