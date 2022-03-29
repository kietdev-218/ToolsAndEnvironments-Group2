package com.example.blockbanking.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.blockbanking.Handling.TranferHandling;
import com.example.blockbanking.MainActivity;
import com.example.blockbanking.Models.Block;
import com.example.blockbanking.Models.Data;
import com.example.blockbanking.Models.User;
import com.example.blockbanking.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Tranfer extends AppCompatActivity{
    private EditText numcardsend, moneyneedsend, contentsend;
    private Button buttonsend;
    private Bundle bundle;
    private String uid;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private User usend, utake;
    private List<User> userList;
    private List<Block> blockList;
    private TranferHandling tranferHandling;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tranfer);
        getSupportActionBar().setTitle("Send");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        GetID();
        userList = new ArrayList<>();
        blockList = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.addListenerForSingleValueEvent(valueEventListener);


        bundle = getIntent().getExtras();
        if (bundle != null){
            uid = bundle.getString("uid");
        }

        tranferHandling = new TranferHandling();
        usend = new User();
        utake = new User();

        numcardsend.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    String number = numcardsend.getText().toString();
                    if (tranferHandling.FindUserTake(userList,number) != null){
                        utake = tranferHandling.FindUserTake(userList,number);
                    } else {
                        numcardsend.setError("Don't find account");
                    }
                    usend = tranferHandling.FindUserSend(userList,uid);
                }
            }
        });


        buttonsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mess = contentsend.getText().toString();
                String money = moneyneedsend.getText().toString();
                if (!(mess.isEmpty() || money.isEmpty() || utake == null)){
                        if ((tranferHandling.CheckZeroMoney(money))) {
                            if (tranferHandling.CheckMoney(usend,money)) {
                                Send(usend,utake,money);
                                Data data = new Data(usend.getName(),utake.getName(),money,mess);
                                His(data,usend.getUid(),utake.getUid());
                                Toast.makeText(Tranfer.this, "Send done", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();
                            } else {
                                moneyneedsend.setError("Not enough money");
                            }
                        } else {
                            moneyneedsend.setError("don't 0 money");
                        }
                }else {
                    Toast.makeText(Tranfer.this, "Input", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void His(Data data, String uid, String uid1) {
        Block lastblock = tranferHandling.GetLastBlock(blockList);
        Block block = new Block(data,uid,uid1,lastblock.getHash());
        block.setX(lastblock.getX()+1);
        databaseReference.child("HisTransaction").child(lastblock.getX()+1+"").setValue(block);
        databaseReference.child("AntiHack").child(lastblock.getX()+1+"").setValue(block);
    }

    private void Send(User s, User t, String money) {
        long mo = Long.valueOf(money);
        databaseReference.child("Users").child(s.getUid()).runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                User users = currentData.getValue(User.class);
                if (users == null){
                    return Transaction.success(currentData);
                } else {
                    users.setMoney(s.getMoney()-mo);
                    currentData.setValue(users);
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });
        databaseReference.child("Users").child(t.getUid()).runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                User usert = currentData.getValue(User.class);
                if (usert == null){
                    return Transaction.success(currentData);
                } else {
                    usert.setMoney(t.getMoney()+mo);
                    currentData.setValue(usert);
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });
    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            userList.clear();
            Iterable<DataSnapshot> userlist = snapshot.child("Users").getChildren();
            for (DataSnapshot dataSnapshot : userlist){
                User user = dataSnapshot.getValue(User.class);
                userList.add(user);
            }
            blockList.clear();
            Iterable<DataSnapshot> blocklist = snapshot.child("HisTransaction").getChildren();
            for (DataSnapshot dataSnapshot2 : blocklist){
                Block block = dataSnapshot2.getValue(Block.class);
                blockList.add(block);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    private void GetID() {
        numcardsend = findViewById(R.id.numbersends);
        moneyneedsend = findViewById(R.id.moneysendd);
        contentsend = findViewById(R.id.contentmess);
        buttonsend = findViewById(R.id.send);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
        return super.onOptionsItemSelected(item);
    }
}