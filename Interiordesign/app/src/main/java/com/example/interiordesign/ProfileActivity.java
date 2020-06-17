package com.example.interiordesign;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class ProfileActivity extends AppCompatActivity {
    TextView textView;
    FirebaseAuth mFirebaseAuth;
    String user;
    private DatabaseReference mDataReference;
    private StorageReference imageReference;
    private StorageReference fileRef;
    private RecyclerView rcvListImg;
    private FirebaseRecyclerAdapter<UploadInfo, ImgViewholder> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        textView=findViewById(R.id.textView5);
        user= Objects.requireNonNull(mFirebaseAuth.getCurrentUser()).getEmail();
        String welcomemsg="Welcome "+user;
        textView.setText(welcomemsg);
        rcvListImg=findViewById(R.id.rcv_list_img);
        mDataReference = FirebaseDatabase.getInstance().getReference(user);
        imageReference = FirebaseStorage.getInstance().getReference().child(user);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(false);
        rcvListImg.setHasFixedSize(false);
        rcvListImg.setLayoutManager(linearLayoutManager);
        Query query = mDataReference.limitToLast(3);

        mAdapter = new FirebaseRecyclerAdapter<UploadInfo, ImgViewholder>(
                UploadInfo.class, R.layout.viewholder, ImgViewholder.class, query) {
            @Override
            protected void populateViewHolder(ImgViewholder viewHolder, UploadInfo model, int position) {
                Picasso.with(ProfileActivity.this)
                        .load(model.url)
                        .error(R.drawable.common_google_signin_btn_icon_dark)
                        .into(viewHolder.imageView);
            }
        };
        rcvListImg.setAdapter(mAdapter);
    }

}
