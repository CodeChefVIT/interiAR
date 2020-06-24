package com.codechefvit.interiAR;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImgViewholder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public ImgViewholder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.img_view);
    }
}
