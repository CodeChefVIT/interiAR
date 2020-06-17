package com.example.interiordesign;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;


public class ProfileActivity extends AppCompatActivity {
    TextView textView;
    FirebaseAuth mFirebaseAuth;
    String user;
    String ssfile;
    private GridView gridView;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private StorageReference imageReference;
    private StorageReference fileRef;
    private StorageReference listRef;
    private ArrayList<String> imageUrls=new ArrayList<>();
    private ArrayList<String> imageNames=new ArrayList<>();
    private FirebaseRecyclerAdapter<UploadInfo, ImgViewholder> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        textView=findViewById(R.id.textView5);
        mFirebaseAuth=FirebaseAuth.getInstance();
        user= Objects.requireNonNull(mFirebaseAuth.getCurrentUser()).getEmail();
        String welcomemsg="Welcome "+user;
        ssfile=user.replace('.','_');
        textView.setText(welcomemsg);
        gridView=findViewById(R.id.grid);
        listAllFiles();
    }
    public void listAllFiles() {
        storage = FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        listRef = storageReference.child(ssfile);
        listRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        for (StorageReference item : listResult.getItems()) {
                            item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imageUrls.add(uri.toString());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ProfileActivity.this,"Files could not be downloaded!",Toast.LENGTH_SHORT).show();
                                }
                            });
                            imageNames.add(item.getName());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this,"Files could not be listed!",Toast.LENGTH_SHORT).show();
                    }
                });
        fillGridView();
    }
    public void fillGridView(){
        GridAdapter adapter=new GridAdapter(ProfileActivity.this,imageUrls,imageNames);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ProfileActivity.this,"You clicked an image",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
