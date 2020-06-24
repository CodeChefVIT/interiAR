package com.codechefvit.interiAR;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

public class MainActivity extends BaseActivity {
    EditText emailid,password,confpass;
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
        String pass=password.getText().toString();
        confpass=findViewById(R.id.editText6);
        String conf=confpass.getText().toString();
        btnsignup=findViewById(R.id.button);
        tvsignin=findViewById(R.id.textView);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals(confpass.getText().toString())) {
                    if (validateForm()) {
                        progressDialog = new ProgressDialog(MainActivity.this);
                        progressDialog.setMessage("Loading....");
                        progressDialog.show();
                        createAccount(emailid.getText().toString().trim(), password.getText().toString().trim());
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"Your passwords do not match! Try Again ",Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Credentials" , pass + " " + conf );
                startActivity(new Intent(MainActivity.this,MainActivity7.class));
            }
        });
        findViewById(R.id.imageView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Credentials" , pass + " " + conf );
                startActivity(new Intent(MainActivity.this,FirstActivity.class));
            }
        });
    }
    private boolean validateForm(){
        boolean valid=true;
        String email=emailid.getText().toString().trim();
        String pwd=password.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            emailid.setError("Required");
            Toast.makeText(this, "Please Enter An Email Id To Continue", Toast.LENGTH_SHORT).show();
            valid=false;
        }
        else{
            emailid.setError(null);
        }
        if(TextUtils.isEmpty(pwd)){
            password.setError("Required");
            Toast.makeText(this, "Please Enter A Password To Continue", Toast.LENGTH_SHORT).show();
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
        mFirebaseAuth.createUserWithEmailAndPassword(email.trim(),pwd.trim())
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            startActivity(new Intent(MainActivity.this,CategoryActivity.class));
                        }
                        else{
                            progressDialog.dismiss();
                            if (pwd.length() < 8 )
                                Toast.makeText(MainActivity.this, "The Minumum Length Of Password must be 8 ", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(MainActivity.this,"Registration unsuccessful! Please try again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        hideProgressDialog();
    }
}
