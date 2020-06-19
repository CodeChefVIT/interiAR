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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity2 extends AppCompatActivity{
    private RecyclerView recyclerView;
    private int[] images;//={R.drawable.i202006140019,R.drawable.i202006140021,R.drawable.i202006140025,R.drawable.i202006140038};
    private String [] names;//={"chairsingleton","comfycouch","sofasingle","sofabitgood"};
    private RecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        int category=getIntent().getIntExtra("selected_category",0);
        String cat="";
        switch(category)
        {
            case 0:
                cat="Sofa";
                images= new int[]{R.drawable.i202006140019, R.drawable.i202006140021, R.drawable.i202006140025, R.drawable.i202006140038};
                names= new String[]{"chairsingleton", "comfycouch", "sofasingle", "sofabitgood"};
                break;
            case 1:
                cat="Chairs";
                images= new int[]{R.drawable.i202006140020,R.drawable.i202006140030,R.drawable.i202006180043,R.drawable.i202006180047,
                        R.drawable.i202006180048,R.drawable.i202006180049,R.drawable.i202006180050,R.drawable.i202006180052};
                names= new String[]{"chairsingle","chairthodiacchiwaali","chair9","chair8","chair4","chair3","chair5","chair7"};
                break;
            case 2:
                cat="Tables";
                images= new int[]{R.drawable.i202006140016,R.drawable.i202006140024,R.drawable.i202006140029,R.drawable.i202006140040,
                        R.drawable.i202006140042,R.drawable.i202006140044,R.drawable.i202006140018,R.drawable.i202006180053,R.drawable.i202006180046,
                        R.drawable.i202006180054,R.drawable.i202006180055,R.drawable.i202006180058};
                names= new String[]{"artistschool","kitchentable","desk","diningtablemodel","lowspeeddinningtable", "jocodiningtable","Bedsidetable",
                        "table1","table2","table3","table4","Teacher_desk"};
                break;
            case 3:
                cat="Sideboards";
                images= new int[]{R.drawable.i202006140032,R.drawable.i202006140035,R.drawable.i202006140039,R.drawable.i202006180060};
                names= new String[]{"sideboard","dressingtableparttwo","shellabitlow","misc8"};
                break;
            case 4:
                cat="Miscellaneous";
                images= new int[]{R.drawable.i202006140017,R.drawable.i202006140022,R.drawable.i202006140023, R.drawable.i202006140026,
                        R.drawable.i202006140027,R.drawable.i202006140028,R.drawable.i202006140034,R.drawable.i202006180056,R.drawable.i202006180045,
                        R.drawable.i202006180044,R.drawable.i202006180057,R.drawable.i202006180059,R.drawable.i202006180042,R.drawable.i202006180041,
                        R.drawable.i202006180040,R.drawable.i202006180039,R.drawable.i202006180038,R.drawable.i202006180037,R.drawable.i202006180035,
                        R.drawable.i202006180036};
                names= new String[]{"bedroom","dressingtable","hearth","tv","sleeepingtable","fireplace","jummer","misc1","misc2","misc3","misc4",
                        "misc6","misc7","misc9","misc10","misc11","misc12","misc13","misc14","misc15"};
                break;
        }
        TextView textView=findViewById(R.id.textView11);
        textView.setText(cat);
        recyclerView=findViewById(R.id.recyclerview);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(layoutManager);
        adapter=new RecyclerAdapter(images,names,cat,this);
        recyclerView.setAdapter(adapter);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.example_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.item1){
                    Intent intent1=new Intent(MainActivity2.this,ProfileActivity.class);
                    startActivity(intent1);
                    return true;
                }
                else if (item.getItemId()==R.id.item2){
                    FirebaseAuth.getInstance().signOut();
                    Intent intent=new Intent(MainActivity2.this,FirstActivity.class);
                    startActivity(intent);
                    return true;
                }
                else {
                    return false;
                }
            }
        });
    }

}
