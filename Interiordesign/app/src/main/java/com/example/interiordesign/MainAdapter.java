package com.example.interiordesign;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MainAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private int[] images;
    private String[] itemnames;

    public MainAdapter(Context c,int[] images,String[] itemnames){
        context=c;
        this.images=images;
        this.itemnames=itemnames;
    }

    @Override
    public int getCount() {
        return images.length;
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
        if (inflater==null){
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null){
            convertView=inflater.inflate(R.layout.grid_item,null);
        }
        ImageView imageView=convertView.findViewById(R.id.image_view);
        Glide.with(context)
                .asBitmap()
                .load(images[position])
                .into(imageView);
        TextView textView=convertView.findViewById(R.id.textView9);
        textView.setText(itemnames[position]);
        return convertView;
    }
}
