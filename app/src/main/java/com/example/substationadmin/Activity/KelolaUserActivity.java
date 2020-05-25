package com.example.substationadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.substationadmin.Adapter.KelolaUserAdapter;
import com.example.substationadmin.Adapter.PengaduanAdapter;
import com.example.substationadmin.Model.Pengaduan;
import com.example.substationadmin.Model.User;
import com.example.substationadmin.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class KelolaUserActivity extends AppCompatActivity {

    RecyclerView rvKelolaUser;
    ArrayList<User> listUser;
    KelolaUserAdapter adapter;

    DatabaseReference rootRef;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_user);

        rvKelolaUser = findViewById(R.id.rvKelolaUser);

        rootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        FloatingActionButton fabAddUser = findViewById(R.id.fabAddUser);
        fabAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAddUser = new Intent(KelolaUserActivity.this, AddUserActivity.class);
                startActivity(intentAddUser);
            }
        });

        ShowRecycleView();
    }

    private void ShowRecycleView() {
        rootRef.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listUser = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    User user = child.getValue(User.class);

                    listUser.add(user);
                }

                adapter = new KelolaUserAdapter(getApplicationContext(), listUser);
                adapter.notifyDataSetChanged();
                rvKelolaUser.setLayoutManager(new LinearLayoutManager(KelolaUserActivity.this));
                rvKelolaUser.setHasFixedSize(true);
                rvKelolaUser.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}