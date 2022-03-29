package com.example.blockbanking.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blockbanking.MainActivity;
import com.example.blockbanking.Models.User;
import com.example.blockbanking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserInfo extends AppCompatActivity implements ValueEventListener {
    private TextView pname, pbirth, pmail, pmoney ,pcard, pphone;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Bundle bundle;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        getSupportActionBar().setTitle("User info");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GetID();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users");
        databaseReference.addValueEventListener(this);

        bundle = getIntent().getExtras();
        if (bundle != null){
            uid = bundle.getString("uid");
        }
    }

    private void UpdateProDisplay(User user){
        pname.setText(user.getName());
        pmail.setText(user.getEmail());
        pphone.setText(user.getPhoneNum());
        pcard.setText(user.getNumberCard());
        pmoney.setText(user.getMoney()+" VND");
        pbirth.setText(user.getBirthday());
    }

    private void GetID() {
        pname = (TextView) findViewById(R.id.proname);
        pbirth = (TextView) findViewById(R.id.probirth);
        pmail = (TextView) findViewById(R.id.promail);
        pmoney = (TextView) findViewById(R.id.promoney);
        pcard = (TextView) findViewById(R.id.pronumbercard);
        pphone = (TextView) findViewById(R.id.prophone);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        User user = snapshot.child(uid).getValue(User.class);
        UpdateProDisplay(user);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}