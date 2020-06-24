package com.codechefvit.interiAR;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class DisplayImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        Window window=this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));
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
