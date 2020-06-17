package com.example.interiordesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class CategoryActivity extends AppCompatActivity {

    GridView gridView;
    int [] images={R.drawable.insideweatherdbvyvunsplash,R.drawable.tatisubbotaw8vb6kwunsplash,R.drawable.ruslanbardash4kbtunsplash,R.drawable.charlesloyer6fdunsplash};
    String [] itemnames={"Sofa","Chairs","Tables","Miscellaneous"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        gridView=findViewById(R.id.grid_view);
        MainAdapter adapter=new MainAdapter(CategoryActivity.this,images,itemnames);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(CategoryActivity.this,MainActivity2.class));
            }
        });
    }
}
