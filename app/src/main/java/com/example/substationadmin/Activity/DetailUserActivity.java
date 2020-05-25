package com.example.substationadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.substationadmin.Model.User;
import com.example.substationadmin.R;
import com.example.substationadmin.UserSerializable;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailUserActivity extends AppCompatActivity {
    EditText etID, etEmail;
    Spinner spWilayah, spJabatan;
    Button btnTombol;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);

        database = FirebaseDatabase.getInstance();

        etID = findViewById(R.id.etID);
        etEmail = findViewById(R.id.etEmail);
        spWilayah = findViewById(R.id.spWilayah);
        spJabatan = findViewById(R.id.spJabatan);

        btnTombol = findViewById(R.id.btnTombol);

        final String [] wilayah = getResources().getStringArray(R.array.jenis_wilayah);

        final User user = getIntent().getParcelableExtra("data");
        etID.setText(user.getId());
        etEmail.setText(user.getEmail());

        if(user.getJabatan().equals("User")){
            spJabatan.setSelection(0);
        }else if(user.getJabatan().equals("Admin")){
            spJabatan.setSelection(1);
        }

        if(user.getWilayah().equals("0")){
            spWilayah.setSelection(0);
        }else if(user.getWilayah().equals("1")){
            spWilayah.setSelection(1);
        }else if(user.getWilayah().equals("2")){
            spWilayah.setSelection(2);
        }else if(user.getWilayah().equals("3")){
            spWilayah.setSelection(3);
        }else if(user.getWilayah().equals("4")){
            spWilayah.setSelection(4);
        }else if(user.getWilayah().equals("5")){
            spWilayah.setSelection(5);
        }else if(user.getWilayah().equals("6")){
            spWilayah.setSelection(6);
        }else if(user.getWilayah().equals("7")){
            spWilayah.setSelection(7);
        }else if(user.getWilayah().equals("8")){
            spWilayah.setSelection(8);
        }else if(user.getWilayah().equals("9")){
            spWilayah.setSelection(9);
        }else if(user.getWilayah().equals("10")){
            spWilayah.setSelection(10);
        }else if(user.getWilayah().equals("11")){
            spWilayah.setSelection(11);
        }else if(user.getWilayah().equals("12")){
            spWilayah.setSelection(12);
        }else if(user.getWilayah().equals("13")){
            spWilayah.setSelection(13);
        }else if(user.getWilayah().equals("14")){
            spWilayah.setSelection(14);
        }else if(user.getWilayah().equals("15")){
            spWilayah.setSelection(15);
        }else if(user.getWilayah().equals("16")){
            spWilayah.setSelection(16);
        }

//        if(getIntent().getParcelableExtra("data") != null){
//            User user = getIntent().getParcelableExtra("data");
//            etID.setText(user.getId());
//            etEmail.setText(user.getEmail());
//            etJabatan.setText(user.getJabatan());
//            etWilayah.setText(wilayah[Integer.valueOf(user.getWilayah())]);
//        }
//        else {
//            Toast.makeText(this, "Kosong", Toast.LENGTH_SHORT).show();
//        }

        etID.setEnabled(false);
        etEmail.setEnabled(false);
        spWilayah.setEnabled(false);
        spJabatan.setEnabled(false);

        btnTombol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnTombol.getText().equals("UPDATE")){
                    etID.setEnabled(true);
                    etEmail.setEnabled(true);
                    spJabatan.setEnabled(true);
                    spWilayah.setEnabled(true);

                    btnTombol.setText("SELESAI");
                }
                else{
                    Toast.makeText(DetailUserActivity.this, "Blum bisa update yaa", Toast.LENGTH_SHORT).show();

                    database.getReference().child("User").orderByChild("uid").equalTo(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                                final String UserKey = dataSnapshot1.getKey();

                                database.getReference().child("User").child(UserKey).child("email").setValue(etEmail.getText().toString());
                                database.getReference().child("User").child(UserKey).child("id").setValue(etID.getText().toString());
                                database.getReference().child("User").child(UserKey).child("jabatan").setValue(spJabatan.getSelectedItem().toString());
                                database.getReference().child("User").child(UserKey).child("wilayah").setValue(spWilayah.getSelectedItemPosition());

                                etID.setEnabled(false);
                                etEmail.setEnabled(false);
                                spJabatan.setEnabled(false);
                                spWilayah.setEnabled(false);

                                btnTombol.setText("UPDATE");
                            }
                            Snackbar.make(btnTombol, "Update data berhasil", Snackbar.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }
}
