package com.example.interiordesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends BaseActivity {
    EditText emailid,password;
    Button btnsignup;
    TextView tvsignin;
    FirebaseAuth mFirebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window=this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimary));
        mFirebaseAuth=FirebaseAuth.getInstance();
        emailid=findViewById(R.id.editText);
        password=findViewById(R.id.editText2);
        btnsignup=findViewById(R.id.button);
        tvsignin=findViewById(R.id.textView);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()){
                    progressDialog=new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Loading....");
                    progressDialog.show();
                    createAccount(emailid.getText().toString(),password.getText().toString());
                }
            }
        });
        tvsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainActivity7.class));
            }
        });
        findViewById(R.id.imageView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,FirstActivity.class));
            }
        });
    }
    private boolean validateForm(){
        boolean valid=true;
        String email=emailid.getText().toString();
        String pwd=password.getText().toString();
        if(TextUtils.isEmpty(email)){
            emailid.setError("Required");
            valid=false;
        }
        else{
            emailid.setError(null);
        }
        if(TextUtils.isEmpty(pwd)){
            password.setError("Required");
            valid=false;
        }
        else{
            password.setError(null);
        }
        return valid;
    }
    private void createAccount(String email,String pwd){
        View view=this.getCurrentFocus();
        hideKeyboard(view);
        mFirebaseAuth.createUserWithEmailAndPassword(email,pwd)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            startActivity(new Intent(MainActivity.this,CategoryActivity.class));
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this,"Registration unsuccessful! Please try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        hideProgressDialog();
    }
}
