package com.example.interiordesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class CategoryActivity extends AppCompatActivity {

    GridView gridView;
    int [] images={R.drawable.insideweatherdbvyvunsplash,R.drawable.tatisubbotaw8vb6kwunsplash,R.drawable.ruslanbardash4kbtunsplash,
            R.drawable.sideboard,R.drawable.charlesloyer6fdunsplash};
    String [] itemnames={"Sofa","Chairs","Tables","Sideboards","Miscellaneous"};

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
                Intent intent=new Intent(CategoryActivity.this,MainActivity2.class);
                intent.putExtra("selected_category",position);
                startActivity(intent);
            }
        });
        Toolbar toolbar=findViewById(R.id.toolbar6);
        toolbar.inflateMenu(R.menu.example_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.item1){
                    Intent intent1=new Intent(CategoryActivity.this,ProfileActivity.class);
                    startActivity(intent1);
                    return true;
                }
                else {
                    return false;
                }
            }
        });
        findViewById(R.id.imageView5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CategoryActivity.super.onBackPressed();
            }
        });
    }
}
