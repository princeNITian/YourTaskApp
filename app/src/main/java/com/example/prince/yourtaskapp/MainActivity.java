package com.example.prince.yourtaskapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText pass;
    private Button login_btn;
    private TextView signup_txt;
    private ProgressDialog mDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.email_login);
        pass = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);
        signup_txt = findViewById(R.id.signup_txt);

        mDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

//        if(mAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
//        }

        signup_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegistrationActivity.class));
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // will be implemented shortly.
                String mEmail = email.getText().toString().trim();
                String mPass = pass.getText().toString().trim();
                if(TextUtils.isEmpty(mEmail)){
                    email.setError("Required Field...");
                    return;
                }
                if(TextUtils.isEmpty(mPass)){
                    pass.setError("Required Field...");
                    return;
                }

                mDialog.setMessage("Processing..");
                mDialog.show();

                mAuth.signInWithEmailAndPassword(mEmail,mPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            Toast.makeText(getApplicationContext(),"Successful",Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();

                        }else{
                            Toast.makeText(getApplicationContext(),"Failed..",Toast.LENGTH_SHORT).show();
                            mDialog.dismiss();

                        }
                    }
                });
            }
        });
    }
}
