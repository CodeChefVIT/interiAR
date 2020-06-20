package com.example.interiordesign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> imageUrls;
    private LayoutInflater layoutInflater;

    public GridAdapter(Context context,ArrayList<String> imageUrls){
        this.context=context;
        this.imageUrls=imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (layoutInflater==null){
            layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null){
            convertView=layoutInflater.inflate(R.layout.resultsitem,null);
        }
        ImageView imageView=convertView.findViewById(R.id.image_view2);
        Glide.with(context)
                .asBitmap()
                .load(imageUrls.get(position))
                .into(imageView);
        return convertView;
    }

}
