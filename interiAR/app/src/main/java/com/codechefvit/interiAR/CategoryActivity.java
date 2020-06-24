package com.codechefvit.interiAR;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

public class CategoryActivity extends AppCompatActivity {

    GridView gridView;
    int [] images={R.drawable.insideweatherdbvyvunsplash,R.drawable.tatisubbotaw8vb6kwunsplash,R.drawable.ruslanbardash4kbtunsplash,
            R.drawable.sideboard,R.drawable.charlesloyer6fdunsplash};
    String [] itemnames={"Sofa","Chairs","Tables","Sideboards","Miscellaneous"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Window window=this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));
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
