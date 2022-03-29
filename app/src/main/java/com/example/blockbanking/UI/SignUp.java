package com.example.blockbanking.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blockbanking.MainActivity;
import com.example.blockbanking.Models.Block;
import com.example.blockbanking.Models.Data;
import com.example.blockbanking.Models.User;
import com.example.blockbanking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class SignUp extends AppCompatActivity {
    private EditText email, name, phone, birthday, pass, confiPasss;
    private Button sync;
    private TextView login;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle("Create New Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GetID();
        //#region birthday setup
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignUp.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        birthday.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        //#endregion

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //#region error
                boolean uempty = false, utrue = true, passtrue = false;
                String uUsername = name.getText().toString();
                String uUserEmail = email.getText().toString();
                String uUserPhone = phone.getText().toString();
                String uUserBirthday = birthday.getText().toString();
                String uUserPass = pass.getText().toString();
                String uUserConPass = confiPasss.getText().toString();
                if (uUsername.isEmpty() || uUserEmail.isEmpty() || uUserPhone.isEmpty() || uUserBirthday.isEmpty() || uUserPass.isEmpty() || uUserConPass.isEmpty()) {
                    Toast.makeText(SignUp.this, "Please complete all information !!!", Toast.LENGTH_SHORT).show();
                    uempty = true;
                }
                if (!(uUserPhone.length() == 10)) {
                    phone.setError("Phone is ten number");
                    utrue = false;
                }
                if (uUserPass.length() < 6) {
                    pass.setError("Password then more 6 char");
                    passtrue = true;
                }
                if (!uUserPass.equals(uUserConPass)) {
                    confiPasss.setError("Password Do not Match");
                    utrue = false;
                }
                //#endregion

                if (uempty == false && utrue == true && passtrue == false) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.createUserWithEmailAndPassword(uUserEmail, uUserPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String uid = firebaseAuth.getCurrentUser().getUid();
                                User user = new User(uUserEmail,uUsername,uUserBirthday,uUserPhone,uid);
                                databaseReference.child("Users").child(uid).setValue(user);
//                                databaseReference.child("HisTransaction").child("0").setValue(generateGenesis());
//                                databaseReference.child("AntiHack").child("0").setValue(generateGenesis());
                                Toast.makeText(SignUp.this, "Sign up done", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(SignUp.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(this, Login.class));
        finish();
        return super.onOptionsItemSelected(item);
    }



    private Block generateGenesis() {
        Block genesis = new Block();
        genesis.setData(new Data("Null","Null","Null","Null"));
        genesis.setPreviousHash(null);
        genesis.setTimeStamp(new Date().toString());
        genesis.setHash(genesis.calculateBlockHash());
        genesis.setX(0);
        genesis.setUids("Null");
        genesis.setUidt("Null");
        return genesis;
    }

    private void GetID() {
        email = findViewById(R.id.userEmail);
        name = findViewById(R.id.userName);
        phone = findViewById(R.id.userPhone);
        birthday = findViewById(R.id.userBirthday);
        pass = findViewById(R.id.password);
        confiPasss = findViewById(R.id.passwordConfirm);
        sync = findViewById(R.id.createAccount);
        login = findViewById(R.id.login);
    }
}