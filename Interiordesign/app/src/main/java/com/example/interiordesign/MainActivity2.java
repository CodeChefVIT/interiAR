package com.example.interiordesign;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity2 extends AppCompatActivity{
    private RecyclerView recyclerView;
    private int[] images={R.drawable.i202006140016,R.drawable.i202006140017,R.drawable.i202006140018, R.drawable.i202006140019,
            R.drawable.i202006140020,R.drawable.i202006140021,R.drawable.i202006140022,R.drawable.i202006140023,R.drawable.i202006140024,
            R.drawable.i202006140025,R.drawable.i202006140026,R.drawable.i202006140027,R.drawable.i202006140028,R.drawable.i202006140029,
            R.drawable.i202006140033,R.drawable.i202006140034,R.drawable.i202006140035,R.drawable.i202006140036,R.drawable.i202006140038,
            R.drawable.i202006140039,R.drawable.i202006140040,R.drawable.i202006140042,R.drawable.i202006140043,R.drawable.i202006140044,
            R.drawable.i202005150005,R.drawable.i202005150006,R.drawable.i202005150007,R.drawable.i202005150008};
    private RecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        recyclerView=findViewById(R.id.recyclerview);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(layoutManager);
        adapter=new RecyclerAdapter(images,this);
        recyclerView.setAdapter(adapter);
        Toolbar toolbar=(Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.example_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.item2:
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(MainActivity2.this,MainActivity7.class);
                startActivity(intent);
            case R.id.item1:
                Intent intent1=new Intent(MainActivity2.this,ProfileActivity.class);
                startActivity(intent1);
        }
        return super.onOptionsItemSelected(item);
    }
}
