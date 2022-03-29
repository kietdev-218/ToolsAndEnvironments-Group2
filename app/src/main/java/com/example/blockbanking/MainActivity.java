package com.example.blockbanking;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blockbanking.Adapter.UserAdapter;
import com.example.blockbanking.Interface.UserEventListener;
import com.example.blockbanking.Models.User;
import com.example.blockbanking.UI.Login;
import com.example.blockbanking.UI.TranHis;
import com.example.blockbanking.UI.Tranfer;
import com.example.blockbanking.UI.UserInfo;
import com.example.blockbanking.UI.Valid;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ValueEventListener, UserEventListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private RecyclerView userList;
    private UserAdapter userAdapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private String uid;
    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GetID();
        setSupportActionBar(toolbar);

        //setup Database
        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Users");
        databaseReference.addValueEventListener(this);

        //#region Navigation View Setup
        navigationView.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        //endregion
    }

    @Override
    protected void onResume() {
        super.onResume();
        LoadUser();
    }

    private void LoadUser() {
        userList.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(getApplicationContext(), users);
        userAdapter.setListener(this);
        userList.setAdapter(userAdapter);
        userAdapter.notifyDataSetChanged();
    }

    private void SetupHeader(User user) {
        View headerView = navigationView.getHeaderView(0);
        TextView displayname = headerView.findViewById(R.id.userDisplayName);
        TextView displayNumCard = headerView.findViewById(R.id.userDisplayEmail);
        TextView displayMoney = headerView.findViewById(R.id.userMoneyDisplay);
        displayname.setText(user.getName());
        displayNumCard.setText(user.getNumberCard());
        displayMoney.setText(user.getMoney() + " VND");
    }

    private void GetID() {
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        userList = findViewById(R.id.user_list);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
                break;
            case R.id.tranfer:
                Intent intent = new Intent(getApplicationContext(), Tranfer.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
                finish();
                break;
            case R.id.valid:
                startActivity(new Intent(getApplicationContext(), Valid.class));
                finish();
                break;
            default:
                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onUserClick(User user) {
        Intent intent = new Intent(getApplicationContext(), TranHis.class);
        intent.putExtra("uid", user.getUid());
        startActivity(intent);
        finish();
    }

    @Override
    public void onUserLongClick(User user) {
        Intent intent = new Intent(getApplicationContext(), UserInfo.class);
        intent.putExtra("uid", user.getUid());
        startActivity(intent);
        finish();
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        Iterable<DataSnapshot> listusers = snapshot.getChildren();
        for (DataSnapshot dataSnapshot : listusers) {
            User user = dataSnapshot.getValue(User.class);
            users.add(user);
        }
        userAdapter.notifyDataSetChanged();
        User my = snapshot.child(uid).getValue(User.class);
        SetupHeader(my);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
}