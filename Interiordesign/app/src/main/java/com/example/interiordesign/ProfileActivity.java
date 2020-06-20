package com.example.interiordesign;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


public class ProfileActivity extends AppCompatActivity {
    TextView textView;
    ImageView profileImageView;
    FirebaseAuth mFirebaseAuth;
    String user;
    String ssfile;
    int TAKE_IMAGE_CODE=10001;
    private GridView gridView;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private StorageReference imageReference;
    private StorageReference fileRef;
    private StorageReference listRef;
    private ArrayList<String> imageUrls=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toast.makeText(getApplicationContext(),"This might take a few moments",Toast.LENGTH_SHORT).show();
        findViewById(R.id.imageView10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileActivity.super.onBackPressed();
            }
        });
        profileImageView=findViewById(R.id.profile);
        textView=findViewById(R.id.textView5);
        mFirebaseAuth=FirebaseAuth.getInstance();
        user= Objects.requireNonNull(mFirebaseAuth.getCurrentUser()).getEmail();
        String welcomemsg="Welcome "+user;
        ssfile=user.replace('.','_');
        textView.setText(welcomemsg);
        gridView=findViewById(R.id.grid);
        if (mFirebaseAuth.getCurrentUser().getPhotoUrl()!=null){
            Glide.with(this)
                    .load(mFirebaseAuth.getCurrentUser().getPhotoUrl())
                    .into(profileImageView);
        }
        listAllFiles();
        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(ProfileActivity.this,FirstActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleImageClick();
            }
        });
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
        GridAdapter adapter=new GridAdapter(ProfileActivity.this,imageUrls);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url=imageUrls.get(position);
                Intent intent=new Intent(ProfileActivity.this,DisplayImageActivity.class);
                intent.putExtra("item",url);
                startActivity(intent);
            }
        });
    }

    private void handleImageClick(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent,TAKE_IMAGE_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==TAKE_IMAGE_CODE){
            switch(resultCode){
                case RESULT_OK:
                    Bitmap bitmap= (Bitmap) data.getExtras().get("data");
                    profileImageView.setImageBitmap(bitmap);
                    handleUpload(bitmap);
            }
        }
    }
    private void handleUpload(Bitmap bitmap){
        String user= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        String ssfile=user.replace('.','_');
        StorageReference reference=FirebaseStorage.getInstance().getReference()
                .child(ssfile)
                .child("profilePic.jpg");
        reference.putBytes(baos.toByteArray())
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                getDownloadUrl(reference);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Profile picture could not be uploaded! Please try again",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getDownloadUrl(StorageReference reference){
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                setUserProfileUrl(uri);
            }
        });
    }
    private void setUserProfileUrl(Uri uri){
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest request=new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();
        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(),"Profile picture updated",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Profile picture failed",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
