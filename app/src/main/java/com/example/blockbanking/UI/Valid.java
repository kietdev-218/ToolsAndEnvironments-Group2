package com.example.blockbanking.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blockbanking.MainActivity;
import com.example.blockbanking.Models.Block;
import com.example.blockbanking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Valid extends AppCompatActivity implements ValueEventListener {
    private TextView textView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<Block> blocks1, blocks2;
    private boolean x = true;
    private Button buttonfix;      
    private int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valid);
        getSupportActionBar().setTitle("Valid");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView = findViewById(R.id.tvcheck);
        buttonfix = findViewById(R.id.btnFix);               
        blocks1 = new ArrayList<>();
        blocks2 = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.addListenerForSingleValueEvent(this);

        buttonfix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("HisTransaction").child(index + "").setValue(blocks2.get(index));
                Toast.makeText(getApplicationContext(), "Fix Done", Toast.LENGTH_SHORT).show();         
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        blocks1.clear();
        blocks2.clear();
        Iterable<DataSnapshot> blocklist = snapshot.child("HisTransaction").getChildren();
        for (DataSnapshot dataSnapshot2 : blocklist) {
            Block block = dataSnapshot2.getValue(Block.class);
            blocks1.add(block);
        }
        Iterable<DataSnapshot> blocklist2 = snapshot.child("AntiHack").getChildren();
        for (DataSnapshot dataSnapshot3 : blocklist2) {
            Block block2 = dataSnapshot3.getValue(Block.class);
            blocks2.add(block2);
        }
        if (blocks1.size() == blocks2.size()) {
            for (int i = 1; i < blocks1.size(); i++) {
                if (CheckBlock(blocks1.get(i), blocks2.get(i)) == false || CheckChain(blocks1.get(i), blocks1.get(i - 1)) == false) {
                    x = false;
                    index = i;
                    break;
                }
            }
        } else {
            x = false;
        }
        if (x == true) {
            textView.setText("Chain is valid");
        } else {
            textView.setText("Chain isn't valid");
            buttonfix.setVisibility(View.VISIBLE);     
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }

    private boolean CheckBlock(Block b1, Block b2) {
        if (b1.getUids().equals(b2.getUids()) && b1.getUidt().equals(b2.getUidt()) && b1.getTimeStamp().equals(b2.getTimeStamp())
                && b1.getHash().equals(b2.getHash()) && b1.getPreviousHash().equals(b2.getPreviousHash()) && b1.getData().getNameTake().equals(b2.getData().getNameTake())
                && b1.getData().getNameSend().equals(b2.getData().getNameSend()) && b1.getData().getMoneySend().equals(b2.getData().getMoneySend()) && b1.getData().getMessenger().equals(b1.getData().getMessenger())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean CheckChain(Block b1, Block b2) {
        if (b1.getPreviousHash().equals(b2.getHash())) {
            return true;
        } else {
            return false;
        }
    }
}