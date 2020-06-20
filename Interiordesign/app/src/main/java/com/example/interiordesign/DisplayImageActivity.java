package com.example.interiordesign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class DisplayImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        ImageView imageView=findViewById(R.id.object);
        String url=getIntent().getStringExtra("item");
        Glide.with(getApplicationContext())
                .load(url)
                .into(imageView);
        findViewById(R.id.imageView9).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayImageActivity.super.onBackPressed();
            }
        });
    }
}
