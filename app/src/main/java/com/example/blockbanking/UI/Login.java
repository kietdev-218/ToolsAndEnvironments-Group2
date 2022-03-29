package com.example.blockbanking.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blockbanking.MainActivity;
import com.example.blockbanking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements FirebaseAuth.AuthStateListener {
    private EditText lemail, lpass;
    private Button llogin;
    private TextView signup, forgetpass;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");
        GetID();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();

        llogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String umail = lemail.getText().toString();
                String upass = lpass.getText().toString();

                if (umail.isEmpty() || upass.isEmpty()){
                    Toast.makeText(Login.this, "Please input user and password ", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.signInWithEmailAndPassword(umail,upass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }else {
                                Toast.makeText(Login.this, "Login Fail", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class));
                finish();
            }
        });

        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,FogetPassword.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.addAuthStateListener(this);
    }

    private void GetID() {
        lemail = findViewById(R.id.lemail);
        lpass = findViewById(R.id.lPassword);
        llogin = findViewById(R.id.loginBtn);
        signup = findViewById(R.id.tvcreateAccount);
        forgetpass = findViewById(R.id.forgotPasword);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

    }
}