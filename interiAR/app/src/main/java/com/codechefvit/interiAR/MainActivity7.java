package com.codechefvit.interiAR;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity7 extends BaseActivity{
    EditText emailid,password;
    Button btnsignin;
    TextView tvsignup;
    FirebaseAuth mFirebaseAuth;
    ProgressDialog progressDialog;
    TextView forgotPassword;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        mFirebaseAuth=FirebaseAuth.getInstance();
        emailid=findViewById(R.id.editText3);
        password=findViewById(R.id.editText4);
        btnsignin=findViewById(R.id.button2);
        tvsignup=findViewById(R.id.textView2);
        Window window=this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));

        forgotPassword=findViewById(R.id.forgetPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity7.this,ForgotPasswordActivity.class));
            }
        });

        mAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser=mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser!=null){
                 Toast.makeText(MainActivity7.this,"You are logged in",Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(MainActivity7.this,MainActivity2.class));
                }
                else{
                    Toast.makeText(MainActivity7.this,"Please Login",Toast.LENGTH_SHORT).show();
                }
            }
        };
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()){
                    progressDialog=new ProgressDialog(MainActivity7.this);
                    progressDialog.setMessage("Loading....");
                    progressDialog.show();
                    signIn(emailid.getText().toString(),password.getText().toString());
                }
            }
        });

        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity7.this,MainActivity.class));
            }
        });

        findViewById(R.id.imageView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity7.this,FirstActivity.class));
            }
        });

    }

    private boolean validateForm(){
        boolean valid=true;
        String email=emailid.getText().toString().trim();
        String pwd=password.getText().toString().trim();
        if (TextUtils.isEmpty(email)){
            emailid.setError("Required");
            valid=false;
        }
        else{
            emailid.setError(null);
        }
        if(TextUtils.isEmpty(pwd)){
            password.setError("Reuired");
            valid=false;
        }
        else {
            password.setError(null);
        }
        return valid;
    }

    private void signIn(String email,String pwd){
        showProgressDialog();
        View view=this.getCurrentFocus();
        hideKeyboard(view);
        mFirebaseAuth.signInWithEmailAndPassword(email.trim(),pwd.trim())
                .addOnCompleteListener(MainActivity7.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            startActivity(new Intent(MainActivity7.this,CategoryActivity.class));
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity7.this,"Login unsuccessful! Please try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        hideProgressDialog();
    }
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
