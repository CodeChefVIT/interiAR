package com.example.interiordesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (firebaseAuth.getCurrentUser()!=null){
                    startActivity(new Intent(SplashActivity.this,MainActivity2.class));
                }
                else{
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                }
                finish();
            }
        },2500);
    }
}
