package com.example.blockbanking.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blockbanking.Adapter.BlockAdapter;
import com.example.blockbanking.MainActivity;
import com.example.blockbanking.Models.Block;
import com.example.blockbanking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TranHis extends AppCompatActivity{
    private RecyclerView blocksList;
    private BlockAdapter blockAdapter;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private List<Block> blocks = new ArrayList<>();
    private Bundle bundle;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tran_his);
        getSupportActionBar().setTitle("Tran his");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GetID();

        bundle = getIntent().getExtras();
        if (bundle != null){
            uid = bundle.getString("uid");
        }

        //setup Database
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("HisTransaction");

        //#region RecyclerView set up
        blocks.clear();
        blocksList.setLayoutManager(new LinearLayoutManager(this));
        blockAdapter = new BlockAdapter(blocks);
        blocksList.setAdapter(blockAdapter);
        //#endregion


        Query query = databaseReference.orderByChild("uids").equalTo(uid);
        query.addValueEventListener(valueEventListener);
        Query query2 = databaseReference.orderByChild("uidt").equalTo(uid);
        query2.addValueEventListener(valueEventListener2);


    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()){
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Block block = dataSnapshot.getValue(Block.class);
                    blocks.add(block);
                }
                blockAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    ValueEventListener valueEventListener2 = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if (snapshot.exists()){
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Block block = dataSnapshot.getValue(Block.class);
                    blocks.add(block);
                }
                blockAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


    private void GetID() {
        blocksList = findViewById(R.id.blockList);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        return super.onOptionsItemSelected(item);
    }
}